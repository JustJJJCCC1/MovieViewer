package com.it2161.dit231980U.movieviewer.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    // State to hold the list of movies
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> get() = _movies

    // State to track loading status
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    // State to hold the details of a selected movie
    private val _movieDetail = MutableStateFlow<MovieDetail?>(null)
    val movieDetail: StateFlow<MovieDetail?> get() = _movieDetail


    // Function to fetch movies based on the selected category
    fun fetchMovies(category: String) {
        viewModelScope.launch {
            _isLoading.value = true // Show loading indicator
            try {
                // Call the appropriate API endpoint based on the category
                val response = when (category) {
                    "Popular" -> RetrofitClient.instance.getPopularMovies()
                    "Top Rated" -> RetrofitClient.instance.getTopRatedMovies()
                    "Now Playing" -> RetrofitClient.instance.getNowPlayingMovies()
                    "Upcoming" -> RetrofitClient.instance.getUpcomingMovies()
                    else -> throw IllegalArgumentException("Invalid category")
                }
                // Update the movies state with the fetched data
                _movies.value = response.results
            } catch (e: Exception) {
                // Handle errors (e.g., show a toast or log the error)
                e.printStackTrace()
            } finally {
                _isLoading.value = false // Hide loading indicator
            }
        }
    }

    fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.instance.getMovieDetails(movieId)
                _movieDetail.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}