package com.it2161.dit231980U.movieviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.it2161.dit231980U.movieviewer.ui.components.LoginScreen
import com.it2161.dit231980U.movieviewer.ui.components.RegisterUserScreen
import com.it2161.dit231980U.movieviewer.ui.components.LandingScreen
import com.it2161.dit231980U.movieviewer.ui.components.MovieDetailScreen
import com.it2161.dit231980U.movieviewer.ui.components.ProfileScreen
import com.it2161.dit231980U.movieviewer.ui.theme.Assignment1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment1Theme {
                val navController = rememberNavController() // Create NavController
                NavHost(navController = navController, startDestination = "login_screen") {
                    composable("login_screen") {
                        LoginScreen(navController)
                    }
                    composable("register_screen") {
                        RegisterUserScreen(navController)
                    }
                    composable("landing_screen") {
                        LandingScreen(navController)
                    }
                    composable("profile_screen") {
                        ProfileScreen(navController)
                    }
                    composable("movie_detail/{movieId}") { backStackEntry ->
                        val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull()
                        movieId?.let { MovieDetailScreen(movieId, navController) }
                    }
                }
            }
        }
    }
}
