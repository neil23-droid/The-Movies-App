# TheMoviesApp

An Android application for browsing upcoming movies and booking tickets, built with Clean Architecture, MVVM, and modern Android libraries.

---

## What you can do

- **Browse upcoming movies** — a scrollable list of movies fetched from The Movie Database (TMDB) API, showing the poster, title, release date, popularity, and an adult-content badge.
- **View movie details** — tap a movie to see its full description, genres, rating, a swipeable poster gallery, and available trailers.
- **Book a ticket** — from the details screen, step through a booking flow to select a location, cinema, and seat, then confirm your booking.
- **View booking history** — access past bookings from the navigation drawer, ordered most-recent-first, each showing the movie poster, title, release date, and your selected location, cinema, and seat.

---

## Architecture overview

The app follows **Clean Architecture** with three strict layers. Each layer has its own data model and no layer reaches across more than one boundary.

```
Network / Database
      ↓  (API models / Entities)
  Data Layer
      ↓  (Domain models)
 Domain Layer
      ↓  (UI models)
Presentation Layer
```

---

## Data layer

**Responsibility:** talk to the network and the local database, hide those details behind repository interfaces, and expose only domain models upward.

### Remote data source

- `MovieRemoteDataSourceImpl` calls the Retrofit interface (`MoviesRemoteApiInterface`) and wraps every response in a `ResponseHandler` sealed class (`Success`, `Error`, `EmptyResponse`).
- `NetworkHandlerImpl` converts a raw `Retrofit Response<T>` into a `ResponseHandler<T>`. It handles HTTP errors, `UnknownHostException`, `SocketTimeoutException`, and `IOException`. It never swallows a `CancellationException`.
- **Knows about:** Retrofit response DTOs (`GetUpComingMoviesResponse`, `GetMovieDetailsResponse`, `GetMoviePostersResponse`, `GetMovieTrailerUrlsResponse`) and `ResponseHandler`. It does **not** know about domain models or entities.

### Local data source

- `MovieLocalDataSourceImpl` wraps DAO calls (`MoviesDao`) in a `LocalResult` sealed class (`Success`, `Error`, `Empty`). There is no `Loading` state because local reads are synchronous.
- `BookedTicketLocalDataSourceImpl` does the same for `BookedTicketHistoryDao`.
- **Knows about:** Room entities (`UpcomingMoviesTable`, `BookedTicketHistoryTable`) and `LocalResult`. It does **not** know about domain models.

### Repositories

- `MoviesRepositoryImpl` orchestrates a **cache-first strategy** for upcoming movies:
  1. If the cached data is less than 30 minutes old (tracked in `AppPreferences`), return it from the local DB.
  2. Otherwise, fetch from the network, save to the local DB, and update the cache timestamp.
  3. On a network failure, return stale cached data as a fallback.
  - For movie details, posters, and trailers, there is no caching — every call goes directly to the network.
- `BookedTicketRepositoryImpl` reads and writes bookings exclusively from the local database.
- Both repositories convert their results to `DomainResult` before returning, so the domain layer never sees `ResponseHandler` or `LocalResult`.

### Data-layer mappers

| Mapper | Converts |
|---|---|
| `UpComingMoviesResponseMapper` | `GetUpComingMoviesResponse` (API DTO) → `List<Movie>` (domain) |
| `MovieDetailsResponseMapper` | `GetMovieDetailsResponse` (API DTO) → `MovieDetail` (domain) |
| `MoviePostersResponseMapper` | `GetMoviePostersResponse` (API DTO) → `MoviePoster` (domain) |
| `MovieTrailerUrlsResponseMapper` | `GetMovieTrailerUrlsResponse` (API DTO) → `MovieTrailer` (domain) |
| `MovieEntityMapper` | `UpcomingMoviesTable` (entity) ↔ `Movie` (domain) |
| `BookedTicketEntityMapper` | `BookedTicketHistoryTable` (entity) ↔ `BookedTicket` (domain) |

---

## Domain layer

**Responsibility:** encode business rules and orchestration. This layer is framework-independent — no Android imports, no Room annotations, no Retrofit types.

### Domain models

Plain Kotlin data classes with no annotations:

- `Movie` — id, title, overview, poster path, release date, vote average, popularity, genre IDs, adult flag.
- `MovieDetail` — everything in `Movie`, plus budget, revenue, runtime, status, tagline, and a `List<MovieGenre>`.
- `MoviePoster` — movie ID and a flat `List<String>` of poster file paths.
- `MovieTrailer` — movie ID and a `List<TrailerInfo>` (YouTube key, site, type, name, official flag).
- `BookedTicket` — movie metadata plus `selectedLocation`, `selectedCinema`, `selectedSeat`, and `bookingDate` (epoch milliseconds).

### Domain result type

`DomainResult` is a sealed class used by all repositories: `Success<T>`, `Error(message, throwable)`, `Loading`, `Empty`. HTTP status codes are stripped here — the UI only ever sees a message string.

### Use cases

| Use case | Business logic |
|---|---|
| `DisplayUpcomingMoviesUseCase` | Delegates to the repository; no extra logic. |
| `GetMovieDetailUseCase` | Loads details, posters, and trailers (separate calls). |
| `BookTicketUseCase` | Validates that location, cinema, seat, and movie title are all present before saving. Returns `DomainResult<Long>` (the inserted row ID). |
| `GetBookedTicketsUseCase` | Fetches all bookings, then **sorts them by `bookingDate` descending** (most-recent-first). This is a business rule and lives here, not in the repository. |

---

## Presentation layer

**Responsibility:** manage UI state, format data for display, and react to user actions. ViewModels depend on use cases only — they never import Room or Retrofit types.

### UI models

Separate from domain models because display requirements differ: dates are pre-formatted, nulls have display-safe defaults, and some fields are combined (e.g., genres joined into a single string).

| UI model | Notable formatting |
|---|---|
| `MoviesListUiModel` | `releaseDate` formatted (e.g., "Mar 15, 2024"), `voteAverage` as string with star, `popularity` as "Trending 🔥", `isAdultContentVisible: Boolean` |
| `MovieDetailsUiModel` | `genres` pre-joined as "Action, Sci-Fi", `voteAverage` as `Float` for `RatingBar` |
| `BookingHistoryUiModel` | All nullable fields have safe defaults ("No location selected", etc.) |
| `BookingSessionModel` | `@Parcelize` data class passed as a Safe Args navigation argument through the booking flow |

### Presentation-layer mappers

| Mapper | Converts |
|---|---|
| `MovieUiMapper` | `Movie` (domain) → `MoviesListUiModel` |
| `MovieDetailsUiMapper` | `MovieDetail` (domain) → `MovieDetailsUiModel` |
| `MoviePostersUiMapper` | `MoviePoster` (domain) → `List<MoviePostersUiModel>` |
| `MovieTrailerUiMapper` | `MovieTrailer` (domain) → `List<MovieTrailerUiModel>` |
| `BookingSessionMapper` | `MovieDetail` (domain) → `BookingSessionModel` (UI); `BookingSessionModel` → `BookedTicket` (domain) |
| `BookedTicketUiMapper` | `BookedTicket` (domain) → `BookingHistoryUiModel` |

### ViewModels

- **`MoviesListingViewModel`** — calls `DisplayUpcomingMoviesUseCase`, emits `StateFlow<UiState<List<MoviesListUiModel>>>`.
- **`MovieDetailsViewModel`** — loads details, posters, and trailers into three separate flows. Holds the raw `MovieDetail` domain model internally so it can produce a `BookingSessionModel` on demand for the booking flow.
- **`LocationDetailsViewModel`** — tracks the user's location, cinema, and seat selections as `StateFlow`. On confirm: merges selections and a `System.currentTimeMillis()` timestamp into the session, calls `BookTicketUseCase`, and emits a `BookTicketUiState`.
- **`BookingHistoryViewModel`** — calls `GetBookedTicketsUseCase` and emits `SharedFlow<UiState<List<BookingHistoryUiModel>>>`.

### UI state types

- `UiState<T>` — `Success<T>`, `Error(message)`, `Loading`, `Empty` — used by all ViewModels.
- `BookTicketUiState` — `Idle`, `Loading`, `Success`, `Error(message)` — specific to the booking confirmation step.

### Screens and navigation

Navigation is handled by a single `NavHostFragment` inside `MoviesHomeActivity` using the Jetpack Navigation Component.

```
MoviesListingFragment
  └─ tap movie → MovieDetailsFragment
                  └─ "Book Ticket" → LocationDetailsFragment
                                      └─ confirm → pop back to MoviesListingFragment

Navigation drawer → BookingHistoryFragment
```

- The drawer is locked closed and can only be opened via the toolbar hamburger icon. Navigation from a drawer item is deferred until the drawer has fully closed to avoid a fragment transaction collision.
- The toolbar icon and title update automatically based on the current destination via `addOnDestinationChangedListener`.

---

## Key libraries

| Library | Purpose |
|---|---|
| Hilt 2.57.1 | Dependency injection |
| Retrofit 2.11.0 + Gson | HTTP client and JSON parsing |
| OkHttp 5.3.2 | HTTP layer with logging |
| Room 2.8.4 | Local SQLite database |
| Kotlin Coroutines 1.10.2 | Async operations |
| Glide 5.0.7 | Image loading |
| Navigation Component 2.7.5 | Single-activity navigation with Safe Args |
| Lifecycle / ViewModel 2.10.0 | MVVM state management |