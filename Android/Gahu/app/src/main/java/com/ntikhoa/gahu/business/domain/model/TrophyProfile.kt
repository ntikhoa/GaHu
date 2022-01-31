package com.ntikhoa.gahu.business.domain.model

data class TrophyProfile(
    val avatar: String,
    val username: String,
    val trophyLevel: Int,
    val trophy: Trophy,
    val gamesPlayed: Int,
    val worldRank: Int,
    val recentPlayed: List<TrophyGame>
)