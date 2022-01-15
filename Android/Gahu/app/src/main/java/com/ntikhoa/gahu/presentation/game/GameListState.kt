package com.ntikhoa.gahu.presentation.game

import com.ntikhoa.gahu.business.domain.model.Game
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

    data class GameState(
        var isLoading: Boolean = false,
        var isExhausted: Boolean = false,
        var games: List<Game>? = null,
        var page: Int = 0,
        var platformIdFilter: String? = null,
        var message: String? = null,
    )
}