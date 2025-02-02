package com.it2161.dit231980U.movieviewer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.it2161.dit231980U.movieviewer.data.MovieViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign

@Composable
fun MovieDetailScreen(movieId: Int, navController: NavController, viewModel: MovieViewModel = viewModel()) {
    val movieDetail by viewModel.movieDetail.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(movieId) {
        viewModel.fetchMovieDetails(movieId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Color.LightGray)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { navController.navigate("landing_screen") }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back Icon",
                    modifier = Modifier.size(32.dp),
                    tint = Color.Black
                )
            }
            Text(
                text = movieDetail?.title ?: "Movie Details",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(32.dp)) // Spacer for the alignment
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Loading indicator
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        } else {
            movieDetail?.let { movie ->
                // Movie Poster
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Title
                Text(
                    text = movie.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                // Movie Info Row
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp) // Space between each line
                ) {
                    // Runtime Header
                    Text(
                        text = "Runtime: ${movie.runtime} min",
                        fontSize = 16.sp,
                        color = Color.Black,
                    )

                    // Release Date
                    Text(
                        text = "Release Date: ${movie.release_date}",
                        fontSize = 16.sp,
                        color = Color.Black
                    )

                    // Revenue
                    Text(
                        text = "Revenue: $${movie.revenue}",
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    // Original Language
                    Text(
                        text = "Original Language: ${movie.original_language.uppercase()}",
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    // Adult
                    Text(
                        text = "Family-Friendly: ${if (movie.adult) "No" else "Yes"}",
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    // Votes
                    Text(
                        text = "Votes: ${movie.vote_count}",
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    //Vote Average
                    Text(
                        text = "Vote Average: ${movie.vote_average}/10",
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Genres
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .horizontalScroll(rememberScrollState()), // Enable horizontal scrolling if genres are too many
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Space between each genre
                ) {
                    Text(
                        text = "Genres:",
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    movie.genres.forEach { genre ->
                        Text(
                            text = genre.name,
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier
                                .background(
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(16.dp) // Rounded corners
                                )
                                .padding(horizontal = 12.dp, vertical = 6.dp) // Padding inside the pill
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Overview
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = "Overview:",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = movie.overview,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

            } ?: Text(
                text = "Movie details not available",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailScreenPreview() {
    MovieDetailScreen(movieId = 1, navController = rememberNavController())
}
