package com.example.composemovieapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composemovieapp.data.repository.MovieRepository
import com.example.composemovieapp.utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val getMovieMutableStateFlow: MutableStateFlow<ApiState> =
        MutableStateFlow(ApiState.Empty)
    val getMovieStateFlow = getMovieMutableStateFlow.asStateFlow()


    init {
        getMovie()
    }


    private fun getMovie() = viewModelScope.launch {
        getMovieMutableStateFlow.emit(ApiState.Loading)
        try {
            movieRepository.getMovies(pageDetail = 3).catch {
                getMovieMutableStateFlow.emit(
                    ApiState.Failure(
                        errorMessage = R.string.generic_error
                    )
                )
            }.collect { data ->
                getMovieMutableStateFlow.emit(ApiState.Success(data = data.result))
            }
        } catch (exception: Exception) {
            getMovieMutableStateFlow.emit(
                ApiState.Failure(
                    errorMessage = R.string.generic_error
                )
            )
        }
    }
}