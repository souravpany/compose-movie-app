package com.example.composemovieapp.data.repository


import com.example.composemovieapp.model.Movie
import com.example.composemovieapp.model.MovieBaseApiResponse

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApiImpl: MovieApiImpl
) {

    /**
     * Function to call  movies api
     */
    fun getMovies(pageDetail: Int): Flow<MovieBaseApiResponse<List<Movie>>> {
        return flow {
            val result = movieApiImpl.getPopularMovie(page = pageDetail)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

}
