package com.ntikhoa.gahu.business.domain.model

data class Trophy(
    val avatar: String,
    val username: String,
    val trophyLevel: Int,
    val totalTrophies: Int,
    val platinum: Int,
    val gold: Int,
    val silver: Int,
    val bronze: Int,
    val gamesPlayed: Int,
    val worldRank: Int
)