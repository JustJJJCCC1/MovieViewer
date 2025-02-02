package com.it2161.dit231980U.movieviewer.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
        private const val BASE_URL = "https://api.themoviedb.org/3/"

    val instance: TmdbApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/") // Base URL for the API
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson for JSON parsing
            .build()
            .create(TmdbApiService::class.java) // Create the API service
    }
}