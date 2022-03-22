package com.example.composemovieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composemovieapp.screen.MovieDetailsScreen
import com.example.composemovieapp.screen.MovieListScreen
import com.example.composemovieapp.utils.Screen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalCoroutinesApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieNav()
        }
    }
}

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun MovieNav() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.MovieListScreen.route
    ) {

        composable(route = Screen.MovieListScreen.route) {
            MovieListScreen(navController = navController)
        }

        composable(route = "${Screen.MovieDetailsScreen.route}/{title}/{releaseDate}/{voteAverage}/{overview}/{posterPath}") {
            val title = it.arguments?.getString("title")
            val releaseDate = it.arguments?.getString("releaseDate")
            val vote = it.arguments?.getString("voteAverage")
            val overView = it.arguments?.getString("overview")
            val posterPath = it.arguments?.getString("posterPath")

            MovieDetailsScreen(
                navController = navController,
                title = title,
                releaseDate = releaseDate,
                vote = vote,
                overView = overView,
                posterPath = posterPath
            )
        }

    }
}