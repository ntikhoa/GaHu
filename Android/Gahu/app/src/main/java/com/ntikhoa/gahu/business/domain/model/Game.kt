package com.ntikhoa.gahu.business.domain.model

import com.ntikhoa.gahu.business.datasource.cache.game.GameEntity


data class Game(
    val id: String,
    val title: String,
    val releaseDate: String,
    val imageUrl: String,
    val description: String,
    val platforms: List<Platform>,
    val author: Author
) {
//    fun toEntity(): GameEntity {
//        return GameEntity(
//            pk = id,
//            title = title,
//            releaseDate = releaseDate,
//            imageUrl = imageUrl,
//            description = description,
//            platformIds = platforms.map { it.id },
//            authorId = author.id,
//            authorName = author.username,
//            authorEmail = author.email,
//        )
//    }
}