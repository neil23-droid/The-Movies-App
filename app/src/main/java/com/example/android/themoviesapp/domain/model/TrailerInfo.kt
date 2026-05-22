package com.example.android.themoviesapp.domain.model

// domain/model/TrailerInfo.kt

data class TrailerInfo(
    val key: String?,           // ← YouTube video key
    val site: String?,          // ← "YouTube"
    val type: String?,          // ← "Trailer", "Teaser"
    val name: String?,
    val isOfficial: Boolean
)
