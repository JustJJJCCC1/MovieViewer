package com.it2161.dit231980U.movieviewer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
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
import com.it2161.dit231980U.movieviewer.MovieRaterApplication
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MovieDetailScreen(movieId: Int, navController: NavController, viewModel: MovieViewModel = viewModel()) {
    val movieDetail by viewModel.movieDetail.collectAsState()
    val movieReviews by viewModel.movieReviews.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val userProfile = MovieRaterApplication.instance.userProfile
    val isFavorite = remember { mutableStateOf(userProfile?.favoriteMovies?.any { it.movieId == movieId } == true) }

    val similarMovies by viewModel.similarMovies.collectAsState()

    LaunchedEffect(movieId) {
        viewModel.fetchMovieDetails(movieId)
        viewModel.fetchMovieReviews(movieId)
        viewModel.fetchSimilarMovies(movieId) // Fetch similar movies
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
            IconButton(onClick = { navController.navigate("landing_screen/Popular") }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back Icon",
                    modifier = Modifier.size(32.dp),
                    tint = Color.Black
                )
            }
            Text(
                text = "Movie Details",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            // Favorite Button
            IconButton(onClick = {
                if (isFavorite.value) {
                    MovieRaterApplication.instance.removeFavoriteMovie(movieId)
                } else {
                    MovieRaterApplication.instance.addFavoriteMovie(movieId)
                }
                isFavorite.value = !isFavorite.value
                navController.navigate("landing_screen/Favourites") // Navigate with category parameter
            }) {
                Icon(
                    imageVector = if (isFavorite.value) Icons.Filled.Delete else Icons.Filled.Add,
                    contentDescription = if (isFavorite.value) "Remove from Favorites" else "Add to Favorites",
                    modifier = Modifier.size(32.dp),
                    tint = if (isFavorite.value) Color.Black else Color.Black
                )
            }
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
        Spacer(modifier = Modifier.height(16.dp))

        // Reviews Section
        if (movieReviews.isNotEmpty()) {
            Text(
                text = "Reviews",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            movieReviews.sortedByDescending { it.created_at }.forEach { review ->                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    // Author and Created At Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = review.author,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        // Formatted Created Date
                        Text(
                            text = formatDate(review.created_at),
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    // Review content inside a disabled outlined TextField
                    OutlinedTextField(
                        value = review.content,
                        onValueChange = {},
                        enabled = false,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledBorderColor = Color.DarkGray,
                            disabledTextColor = Color.DarkGray,
                            disabledSupportingTextColor = Color.DarkGray,
                            disabledLabelColor = Color.DarkGray
                        ),
                        textStyle = LocalTextStyle.current.copy(fontSize = 14.sp, color = Color.Black),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = false,
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        } else {
            Text(
                text = "No reviews available",
                modifier = Modifier.padding(16.dp)
            )
        }

        // Similar Movies Section
        if (similarMovies.isNotEmpty()) {
            Text(
                text = "Similar Movies",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Use a Column to display each similar movie
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                similarMovies.forEach { movie ->
                    MovieItem(movie = movie, navController = navController)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        } else {
            Text(
                text = "No similar movies available",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

// Helper function to format the date
fun formatDate(dateString: String?): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return try {
        val date = inputFormat.parse(dateString)
        outputFormat.format(date ?: Date())
    } catch (e: Exception) {
        "Invalid Date"
    }
}


@Preview(showBackground = true)
@Composable
fun MovieDetailScreenPreview() {
    MovieDetailScreen(movieId = 1, navController = rememberNavController())
}
