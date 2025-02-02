package com.it2161.dit231980U.movieviewer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.it2161.dit231980U.movieviewer.data.Movie
import com.it2161.dit231980U.movieviewer.data.MovieViewModel
import coil.compose.AsyncImage

@Composable
fun LandingScreen(navController: NavController, viewModel: MovieViewModel = viewModel()) {
    // Observe the movies and loading state from the ViewModel
    val movies by viewModel.movies.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // State to track the selected category
    var selectedCategory by remember { mutableStateOf("Popular") }

    // Fetch movies when the selected category changes
    LaunchedEffect(selectedCategory) {
        viewModel.fetchMovies(selectedCategory)
    }

    val scrollState = rememberScrollState()

    // Custom top bar with increased height and profile icon on the right
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp) // Increase the height of the top bar
                .background(Color.Gray)
                .padding(horizontal = 16.dp), // Add some padding around the elements
            horizontalArrangement = Arrangement.End, // Align items to the right
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile icon button on the right
            IconButton(onClick = {
                navController.navigate("profile_screen") // Replace with actual route for profile screen
            }) {
                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Profile Icon", modifier = Modifier.size(32.dp))
            }
        }
        // Category Toggles
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("Popular", "Top Rated", "Now Playing", "Upcoming").forEach { category ->
                Text(
                    text = category,
                    modifier = Modifier
                        .clickable { selectedCategory = category }
                        .background(
                            if (selectedCategory == category) Color.Gray else Color.Transparent
                        )
                        .padding(horizontal = 4.dp, vertical = 4.dp), // Optional: for padding inside the background
                    color = if (selectedCategory == category) Color.White else Color.Black
                )
            }
        }

        // Movie List
        if (isLoading) {
            Text(text = "Loading...", modifier = Modifier.padding(16.dp))
        } else {
            Column {
                // Iterating through the list of movies
                if (isLoading) {
                    Text(text = "Loading...", modifier = Modifier.padding(16.dp))
                } else if (movies.isEmpty()) {
                    Text(text = "No movies available", modifier = Modifier.padding(16.dp))
                } else {
                    movies.forEach { movie ->
                        MovieItem(movie = movie)
                    }
                }

            }
        }
    }
}


@Composable
fun MovieItem(movie: Movie) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movie.poster_path ?: ""}",
            contentDescription = movie.title,
            modifier = Modifier.size(100.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(text = movie.title, style = MaterialTheme.typography.titleMedium)
            Text(text = movie.overview, style = MaterialTheme.typography.bodyMedium)
            Text(text = "Release Date: ${movie.release_date}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LandingScreenPreview() {
    LandingScreen(navController = rememberNavController())
}