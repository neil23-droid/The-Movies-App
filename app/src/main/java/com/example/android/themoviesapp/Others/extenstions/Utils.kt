package com.example.android.themoviesapp.Others.extenstions

import com.example.android.themoviesapp.domain.model.MovieGenre
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

// extension functions keep the mapper clean
fun String?.formatReleaseDate(): String {
    if (this == null) return "Unknown Date"
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        val date = inputFormat.parse(this)
        outputFormat.format(date!!)           // "2024-03-15" → "Mar 15, 2024"
    } catch (e: Exception) {
        this                                   // return original if parsing fails
    }
}

fun Double.toPopularityLabel(): String {
    return when {
        this > 1000 -> "Trending"
        this > 500  -> "Popular"
        else        -> "New"
    }
}

// extension functions
fun Long.formatRuntime(): String {
    if (this == 0L) return "Unknown"
    val hours = this / 60
    val minutes = this % 60
    return "${hours}h ${minutes}m"              // 135 → "2h 15m"
}

fun Long.formatCurrency(): String {
    if (this == 0L) return "N/A"
    return NumberFormat.getCurrencyInstance(Locale.US).format(this)  // 150000000 → "$150,000,000"
}

// genre list → comma separated string
fun List<MovieGenre>.toGenresString(): String {
    return this.mapNotNull { it.name }
        .joinToString(", ")
}

// decimal formatting for RatingBar
fun Double.toRatingFloat(): Float {
    return DecimalFormat("0.0")
        .format(this)
        .toFloat()
}