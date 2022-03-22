package com.example.composemovieapp.model

import com.google.gson.annotations.SerializedName

data class MovieBaseApiResponse<T>(
    @SerializedName("page")
    var page: Int?,
    @SerializedName("results")
    var result: T?,
)