package com.ntikhoa.gahu.business.domain.model

import com.ntikhoa.gahu.business.datasource.cache.game.GameEntity


open class Game(
    val id: String,
    val title: String,
    val releaseDate: String,
    val imageUrl: String,
    val description: String,
    val author: Author
) {
    override fun equals(other: Any?): Boolean {
        if (other is Game)
            return (id == other.id
                    && title == other.title
                    && releaseDate == other.releaseDate
                    && imageUrl == other.imageUrl
                    && description == other.description
                    && author == other.author)
        else return false
    }
}