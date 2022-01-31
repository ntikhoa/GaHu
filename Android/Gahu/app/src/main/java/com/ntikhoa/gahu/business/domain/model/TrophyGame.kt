package com.ntikhoa.gahu.business.domain.model

data class TrophyGame(
    val title: String,
    val imageUrl: String,
    val got: Int,
    val trophy: Trophy
)