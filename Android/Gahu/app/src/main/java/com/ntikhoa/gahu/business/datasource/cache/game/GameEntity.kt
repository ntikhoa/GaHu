package com.ntikhoa.gahu.business.datasource.cache.game

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.ntikhoa.gahu.business.datasource.cache.account.AccountEntity
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
    val platforms: List<String>,

    @ColumnInfo(name = "authorId")
    val authorId: String,

    @ColumnInfo(name = "authorName")
    val authorName: String,
) {

}