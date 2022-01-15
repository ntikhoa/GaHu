package com.ntikhoa.gahu.presentation.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntikhoa.gahu.business.interactor.game.GetGames
import com.ntikhoa.gahu.business.interactor.game.GetPlatforms
import com.ntikhoa.gahu.presentation.CancelJob
import com.ntikhoa.gahu.presentation.OnTriggerEvent
import com.ntikhoa.gahu.presentation.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GameViewModel
@Inject
constructor(
    private val getPlatformsUseCase: GetPlatforms,
    private val getGamesUseCase: GetGames,
    private val sessionManager: SessionManager
) : ViewModel(), OnTriggerEvent<GameEvent>, CancelJob {
    private val TAG = "GameViewModel"

    private val _state: MutableLiveData<GameListState> = MutableLiveData(GameListState())
    val state: LiveData<GameListState> get() = _state

    private var platformJob: Job? = null
    private var gameJob: Job? = null

    override fun onTriggerEvent(event: GameEvent) {
        when (event) {
            is GameEvent.GetPlatforms -> {
                sessionManager.token?.let { token ->
                    getPlatforms(token)
                }
            }
            is GameEvent.GetGames -> {
                sessionManager.token?.let { token ->
                    getGames(token)
                }
            }
        }
    }

    private fun getPlatforms(token: String) {
        _state.value?.let {
            platformJob?.cancel()
            platformJob = getPlatformsUseCase(token).onEach { dataState ->
                _state.value?.platformState?.let {
                    val platformState = it.copy()

                    platformState.isLoading = dataState.isLoading

                    dataState.data?.let { platforms ->
                        platformState.platforms = platforms
                    }

                    dataState.message?.let { msg ->
                        platformState.message = msg
                    }

                    _state.value = _state.value?.copy(platformState = platformState)
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun getGames(token: String) {
        _state.value?.let {
            gameJob?.cancel()
            gameJob = getGamesUseCase(
                token,
                it.gameState.page,
                it.gameState.platformIdFilter
            ).onEach { dataState ->
                _state.value?.gameState?.let {
                    val gameState = it.copy()

                    gameState.isLoading = dataState.isLoading

                    dataState.data?.let { games ->
                        gameState.games = games
                    }

                    dataState.message?.let { msg ->
                        gameState.message = msg
                    }

                    _state.value = _state.value?.copy(gameState = gameState)
                }
            }.launchIn(viewModelScope)
        }
    }

    override fun cancelJob() {
        platformJob?.cancel()
        gameJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }
}