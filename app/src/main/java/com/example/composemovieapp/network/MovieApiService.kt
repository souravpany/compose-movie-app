package com.example.composemovieapp.network


import com.example.composemovieapp.model.Movie
import com.example.composemovieapp.model.MovieBaseApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET(EndPoint.POPULAR_MOVIE)
    suspend fun getPopularMovieData(
        @Query("page") page: Int
    ): MovieBaseApiResponse<List<Movie>>

}