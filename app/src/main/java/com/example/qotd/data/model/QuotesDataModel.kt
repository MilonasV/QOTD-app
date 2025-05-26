package com.example.qotd.data.model

import com.google.gson.annotations.SerializedName

data class Quote(
    val id: Int,
    val quote: String,
    val game: String,
    val character: String
)