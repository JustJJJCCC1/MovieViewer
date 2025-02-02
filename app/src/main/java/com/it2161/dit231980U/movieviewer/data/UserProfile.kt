package com.it2161.dit231980U.movieviewer.data

data class UserProfile(
    val userName: String="",
    val password: String="",
    val email: String="",
    val favoriteMovies: MutableList<FavoriteMovie> = mutableListOf() // List of FavoriteMovie objects
)
