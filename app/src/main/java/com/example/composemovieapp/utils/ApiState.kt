package com.example.composemovieapp.utils

sealed class ApiState {
    /**
     * Loading
     */
    object Loading : ApiState()

    /**
     * Failure response
     */
    class Failure(val errorMessage: Int? = null) : ApiState()

    /**
     * success response with body
     */
    class Success<T>(val data: T) : ApiState()

    /**
     * Empty response
     */
    object Empty : ApiState()
}