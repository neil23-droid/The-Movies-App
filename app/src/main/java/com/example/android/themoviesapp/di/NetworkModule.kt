package com.example.android.themoviesapp.di

import com.example.android.themoviesapp.BuildConfig
import com.example.android.themoviesapp.Others.BASEURL
import com.example.android.themoviesapp.data.remote.api.MoviesRemoteApiInterface
import com.example.android.themoviesapp.data.remote.datasource.MovieRemoteDataSource
import com.example.android.themoviesapp.data.remote.datasource.MovieRemoteDataSourceImpl
import com.example.android.themoviesapp.data.remote.handler.NetworkHandler
import com.example.android.themoviesapp.data.remote.handler.NetworkHandlerImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// di/NetworkModule.kt

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // 1. OkHttpClient — built first, used by Retrofit
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            }
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                }
            }
            .build()
    }

    // 2. Gson — used by GsonConverterFactory
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .serializeNulls()
            .create()
    }

    // 3. GsonConverterFactory — used by Retrofit
    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    // 4. Retrofit — uses OkHttpClient + GsonConverterFactory
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASEURL)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    // 5. API Interface — uses Retrofit
    @Provides
    @Singleton
    fun provideMoviesApi(retrofit: Retrofit): MoviesRemoteApiInterface {
        return retrofit.create(MoviesRemoteApiInterface::class.java)
    }

    // 6. NetworkHandler
    @Provides
    @Singleton
    fun provideNetworkHandler(): NetworkHandler {
        return NetworkHandlerImpl()
    }

    // 7. RemoteDataSource — uses API + NetworkHandler
    @Provides
    @Singleton
    fun provideMovieRemoteDataSource(
        api: MoviesRemoteApiInterface,
        networkHandler: NetworkHandler
    ): MovieRemoteDataSource {
        return MovieRemoteDataSourceImpl(api, networkHandler)
    }
}
