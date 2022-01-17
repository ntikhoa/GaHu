package com.ntikhoa.gahu.business.datasource.cache.game

import androidx.room.*
import androidx.room.util.TableInfo

// remove foreign key for complexity purpose
@Entity(
    tableName = "game_platform",
    primaryKeys = ["game_id", "platform_id"],
//    foreignKeys = [
//        ForeignKey(
//            entity = GameEntity::class,
//            parentColumns = ["pk"],
//            childColumns = ["game_id"],
//            onDelete = ForeignKey.CASCADE,
//            onUpdate = ForeignKey.CASCADE
//        ),
//        ForeignKey(
//            entity = PlatformEntity::class,
//            parentColumns = ["_pk"],
//            childColumns = ["platform_id"],
//            onDelete = ForeignKey.CASCADE,
//            onUpdate = ForeignKey.CASCADE
//        )
//    ],
//    indices = [Index("platform_id")]
)
data class GamePlatformEntity(
    @ColumnInfo(name = "game_id")
    val gameId: String,

    @ColumnInfo(name = "platform_id")
    val platformId: String
)