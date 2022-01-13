package com.ntikhoa.gahu.business.datasource.cache.game

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.ntikhoa.gahu.business.datasource.cache.account.AccountEntity
import com.ntikhoa.gahu.business.domain.model.Author
import com.ntikhoa.gahu.business.domain.model.Game
import com.ntikhoa.gahu.business.domain.model.Platform

@Entity(
    tableName = "games"
)
data class GameEntity constructor(
    @PrimaryKey
    @ColumnInfo(name = "pk")
    var pk: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "release_date")
    val releaseDate: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "author_id")
    val authorId: String,

    @ColumnInfo(name = "author_email")
    val authorEmail: String,

    @ColumnInfo(name = "author_name")
    val authorName: String
) {

//    fun toDomain(): Game {
//        val platforms = platforms.map {
//            it.toDomain()
//        }
//
//        return Game(
//            id = pk,
//            title = title,
//            releaseDate = releaseDate,
//            imageUrl = imageUrl,
//            description = description,
//            platforms = platforms,
//            author = Author(
//                id = authorId,
//                email = authorEmail,
//                username = authorName
//            )
//        )
//    }
}