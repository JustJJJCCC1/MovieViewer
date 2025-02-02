package com.it2161.dit231980U.movieviewer.data

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?, // This is the poster image path
    val release_date: String
)

data class MovieResponse(
    val results: List<Movie> // List of movies returned by the API
)