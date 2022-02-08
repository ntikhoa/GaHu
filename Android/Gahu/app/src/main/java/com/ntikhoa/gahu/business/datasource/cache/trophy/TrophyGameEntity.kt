package com.ntikhoa.gahu.business.datasource.cache.trophy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.ntikhoa.gahu.business.domain.model.Trophy
import com.ntikhoa.gahu.business.domain.model.TrophyGame


@Entity(
    tableName = "trophy_games",
    foreignKeys = [
        ForeignKey(
            entity = TrophyProfileEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class TrophyGameEntity(
    @PrimaryKey
    @ColumnInfo(name = "slug")
    val slug: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "image")
    val imageUrl: String,

    @ColumnInfo(name = "got")
    val got: Int,

    @ColumnInfo(name = "total_trophies")
    val totalTrophies: Int,

    @ColumnInfo(name = "platinum")
    val platinum: Int,

    @ColumnInfo(name = "gold")
    val gold: Int,

    @ColumnInfo(name = "silver")
    val silver: Int,

    @ColumnInfo(name = "bronze")
    val bronze: Int,

    @ColumnInfo(name = "user_id")
    val userId: String
) {
    fun toDomain(): TrophyGame {
        return TrophyGame(
            title = title,
            slug = slug,
            imageUrl = imageUrl,
            got = got,
            trophy = Trophy(
                totalTrophies = totalTrophies,
                platinum = platinum,
                gold = gold,
                silver = silver,
                bronze = bronze
            )
        )
    }
}