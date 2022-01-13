package com.ntikhoa.gahu.business.domain.model

import com.ntikhoa.gahu.business.datasource.cache.game.GameEntity
import com.ntikhoa.gahu.business.datasource.cache.game.GamePlatformEntity
import com.ntikhoa.gahu.business.datasource.cache.game.PlatformEntity

class GameDetail(
    id: String,
    title: String,
    releaseDate: String,
    imageUrl: String,
    description: String,
    author: Author,
    val platforms: List<Platform>?
) : Game(id, title, releaseDate, imageUrl, description, author) {

    fun toGameEntity(): GameEntity {
        return GameEntity(
            pk = id,
            title = title,
            releaseDate = releaseDate,
            imageUrl = imageUrl,
            description = description,
            authorId = author.id,
            authorName = author.username,
            authorEmail = author.email,
        )
    }

    fun toGamePlatformEntity(): List<GamePlatformEntity> {
        val result = ArrayList<GamePlatformEntity>()
        platforms?.let {
            for (platform in it) {
                result.add(
                    GamePlatformEntity(
                        gameId = id,
                        platformId = platform.id
                    )
                )
            }
        }
        return result
    }
}