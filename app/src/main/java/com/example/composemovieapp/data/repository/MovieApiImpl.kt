package com.example.composemovieapp.data.repository

import com.example.composemovieapp.model.Movie
import com.example.composemovieapp.model.MovieBaseApiResponse
import com.example.composemovieapp.network.MovieApiService
import javax.inject.Inject

class MovieApiImpl @Inject constructor(
    private val apiService: MovieApiService,
) {

    /**
     * Api call to get  movies
     */
    suspend fun getPopularMovie(page: Int): MovieBaseApiResponse<List<Movie>> {
        return apiService.getPopularMovieData(page = page)
    }

}