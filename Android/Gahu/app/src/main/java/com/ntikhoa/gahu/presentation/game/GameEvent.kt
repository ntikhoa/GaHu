package com.ntikhoa.gahu.presentation.game

sealed class GameEvent {
    class GetPlatforms : GameEvent()
    class GetGames : GameEvent()
    class GetGamesNextPage : GameEvent()
    data class SetPlatformFilter(val platformFilter: String? = null) : GameEvent()
}