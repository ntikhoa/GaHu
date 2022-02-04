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
        return if (other is Game)
            (id == other.id
                    && title == other.title
                    && releaseDate == other.releaseDate
                    && imageUrl == other.imageUrl
                    && description == other.description
                    && author == other.author)
        else false
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + releaseDate.hashCode()
        result = 31 * result + imageUrl.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + author.hashCode()
        return result
    }
}