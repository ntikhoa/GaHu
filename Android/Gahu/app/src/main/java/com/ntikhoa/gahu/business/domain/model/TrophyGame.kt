package com.ntikhoa.gahu.business.domain.model

import com.ntikhoa.gahu.business.datasource.cache.trophy.TrophyGameEntity

data class TrophyGame(
    val title: String,
    val slug: String,
    val imageUrl: String,
    val got: Int,
    val trophy: Trophy
) {
    fun getCompletion(): Int {
        return  (got * 100 / trophy.totalTrophies)
    }

    fun toEntity(userId: String): TrophyGameEntity {
        return TrophyGameEntity(
            slug = slug,
            title = title,
            imageUrl = imageUrl,
            got = got,
            totalTrophies = trophy.totalTrophies,
            platinum = trophy.platinum,
            gold = trophy.gold,
            silver = trophy.silver,
            bronze = trophy.bronze,
            userId = userId
        )
    }
}