package com.ntikhoa.gahu.business.datasource.cache.game

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.ntikhoa.gahu.business.datasource.cache.account.AccountEntity
import com.ntikhoa.gahu.business.domain.model.Author
import com.ntikhoa.gahu.business.domain.model.Game
import com.ntikhoa.gahu.business.domain.model.Platform

@Entity(
    tableName = "games",
    foreignKeys = [
        ForeignKey(
            entity = PlatformEntity::class,
            parentColumns = ["pk"],
            childColumns = ["pk"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class GameEntity(
    @PrimaryKey
    @ColumnInfo(name = "pk")
    var pk: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "releaseDate")
    val releaseDate: String,

    @ColumnInfo(name = "imageUrl")
    val imageUrl: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "platforms")
    val platformIds: List<String>,

    @ColumnInfo(name = "authorId")
    val authorId: String,

    @ColumnInfo(name = "authorEmail")
    val authorEmail: String,

    @ColumnInfo(name = "authorName")
    val authorName: String,

    @Embedded
    val platforms: List<PlatformEntity>
) {
    fun toDomain(): Game {
        val platforms = platforms.map {
            it.toDomain()
        }

        return Game(
            id = pk,
            title = title,
            releaseDate = releaseDate,
            imageUrl = imageUrl,
            description = description,
            platforms = platforms,
            author = Author(
                id = authorId,
                email = authorEmail,
                username = authorName
            )
        )
    }
}