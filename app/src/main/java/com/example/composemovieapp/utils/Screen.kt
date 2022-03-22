package com.example.composemovieapp.utils


sealed class Screen(val route: String) {
    object MovieListScreen : Screen("movie_list_screen")
    object MovieDetailsScreen : Screen("movie_details_screen")
}
