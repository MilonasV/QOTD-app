package com.example.qotd.data.model

data class Game(
    val game: String,
    val isSelected: Boolean = false,
    val imageId: Int
)