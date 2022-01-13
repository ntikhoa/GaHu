package com.ntikhoa.gahu.business.domain.model

import com.ntikhoa.gahu.business.datasource.cache.game.GameEntity


open class Game(
    val id: String,
    val title: String,
    val releaseDate: String,
    val imageUrl: String,
    val description: String,
    val author: Author
)