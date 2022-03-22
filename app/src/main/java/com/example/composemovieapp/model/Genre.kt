package com.example.composemovieapp.model

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String,
)