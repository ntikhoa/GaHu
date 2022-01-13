package com.ntikhoa.gahu.presentation.game

import com.ntikhoa.gahu.business.domain.model.Platform

data class GameListState(
    val platformState: PlatformState = PlatformState(),
    val gameState: GameState = GameState()
) {
    data class PlatformState(
        var isLoading: Boolean = false,
        var platforms: List<Platform>? = null,
        var message: String? = null
    )

    data class GameState(val isLoading: Boolean = false)
}