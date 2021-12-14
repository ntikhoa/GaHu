package com.ntikhoa.gahu.business.domain.model


data class Game(
    val id: String,
    val title: String,
    val releaseDate: String,
    val imageUrl: String,
    val description: String,
    val platforms: List<Platform>,
    val author: Author
) {
}