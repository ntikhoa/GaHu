package com.ntikhoa.gahu.business.domain.model

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
}