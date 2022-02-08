package com.ntikhoa.gahu.business.datasource.cache.trophy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ntikhoa.gahu.business.domain.model.Trophy
import com.ntikhoa.gahu.business.domain.model.TrophyProfile

@Entity(tableName = "trophy_profiles")
data class TrophyProfileEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "avatar")
    val avatar: String,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "trophy_level")
    val trophyLevel: Int,

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

    @ColumnInfo(name = "games_played")
    val gamesPlayed: Int,

    @ColumnInfo(name = "world_rank")
    val worldRank: Int,
)