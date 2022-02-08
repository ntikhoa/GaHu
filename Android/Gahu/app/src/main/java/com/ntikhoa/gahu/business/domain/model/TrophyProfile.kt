package com.ntikhoa.gahu.business.domain.model

import com.ntikhoa.gahu.business.datasource.cache.trophy.TrophyGameEntity
import com.ntikhoa.gahu.business.datasource.cache.trophy.TrophyProfileEntity

data class TrophyProfile(
    val id: String,
    val avatar: String,
    val username: String,
    val trophyLevel: Int,
    val trophy: Trophy,
    val gamesPlayed: Int,
    val worldRank: Int,
    val recentPlayed: List<TrophyGame>
) {
    companion object {
        fun fromEntityMap(
            profileEntity: TrophyProfileEntity,
            gameEntities: List<TrophyGameEntity>
        ): TrophyProfile {
            return TrophyProfile(
                id = profileEntity.id,
                avatar = profileEntity.avatar,
                username = profileEntity.username,
                trophyLevel = profileEntity.trophyLevel,
                trophy = Trophy(
                    totalTrophies = profileEntity.totalTrophies,
                    platinum = profileEntity.platinum,
                    gold = profileEntity.gold,
                    silver = profileEntity.silver,
                    bronze = profileEntity.bronze
                ),
                gamesPlayed = profileEntity.gamesPlayed,
                worldRank = profileEntity.worldRank,
                recentPlayed = gameEntities.map { it.toDomain() }
            )
        }
    }

    fun toTrophyProfileEntity(): TrophyProfileEntity {
        return TrophyProfileEntity(
            id = id,
            avatar = avatar,
            username = username,
            trophyLevel = trophyLevel,
            totalTrophies = trophy.totalTrophies,
            platinum = trophy.platinum,
            gold = trophy.gold,
            silver = trophy.silver,
            bronze = trophy.bronze,
            gamesPlayed = gamesPlayed,
            worldRank = worldRank,
        )
    }

    fun toTrophyGameEntities(): List<TrophyGameEntity> {
        return recentPlayed.map {
            it.toEntity(userId = id)
        }
    }
}