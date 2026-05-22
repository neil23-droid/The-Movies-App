package com.example.android.themoviesapp.data.remote.api

// data/remote/api/MoviesAppClient.kt

import com.example.android.themoviesapp.BuildConfig
import com.example.android.themoviesapp.Others.BASEURL
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MoviesAppClient {

    val api: MoviesRemoteApiInterface by lazy { createApi() }

    private fun createApi(): MoviesRemoteApiInterface {
        return Retrofit.Builder()
            .client(buildHttpClient())
            .baseUrl(BASEURL)
            .addConverterFactory(buildGsonConverter())
            .build()
            .create(MoviesRemoteApiInterface::class.java)
    }

    private fun buildHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original: Request = chain.request()
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

    private fun buildGsonConverter(): GsonConverterFactory {
        return GsonConverterFactory.create(
            GsonBuilder()
                .serializeNulls()
                .create()
        )
    }
}