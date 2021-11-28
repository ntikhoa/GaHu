package com.ntikhoa.gahu.presentation.game

import com.ntikhoa.gahu.business.domain.model.Platform

data class GameState(
    val isLoading: Boolean = false,
    val platforms: List<Platform>? = null,
    val message: String? = null
)