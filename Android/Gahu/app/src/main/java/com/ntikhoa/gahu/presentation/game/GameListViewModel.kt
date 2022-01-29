package com.ntikhoa.gahu.presentation.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.ntikhoa.gahu.business.domain.util.ErrorHandler
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
class GameListViewModel
@Inject
constructor(
    private val getPlatformsUseCase: GetPlatforms,
    private val getGamesUseCase: GetGames,
    private val sessionManager: SessionManager
) : ViewModel(), OnTriggerEvent<GameEvent>, CancelJob {
    private val TAG = "GameViewModel"

    private val _platformState: MutableLiveData<PlatformState> = MutableLiveData(PlatformState())
    val platformState: LiveData<PlatformState> get() = _platformState

    private val _gameState: MutableLiveData<GameState> = MutableLiveData(GameState())
    val gameState: LiveData<GameState> get() = _gameState

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
                    resetPage()
                    getGames(token)
                }
            }
            is GameEvent.GetGamesNextPage -> {
                sessionManager.token?.let { token ->
                    gameState.value?.let { state ->
                        state.page = state.page + 1
                    }
                    Log.i(TAG, "onTriggerEvent: page ${gameState.value?.page}")
                    getGames(token)
                }
            }
            is GameEvent.SetPlatformFilter -> {
                gameState.value?.platformIdFilter = event.platformFilter
            }
        }
    }

    private fun resetPage() {
        gameState.value?.page = 1
        gameState.value?.isExhausted = false
    }

    private fun getPlatforms(token: String) {
        _platformState.value?.let {
            platformJob?.cancel()
            platformJob = getPlatformsUseCase(token).onEach { dataState ->

                val copiedState = it.copy()

                copiedState.isLoading = dataState.isLoading

                dataState.data?.let { platforms ->
                    Log.i(TAG, "getPlatforms: $platforms")
                    copiedState.platforms = platforms
                }

                dataState.message?.let { msg ->
                    Log.i(TAG, "getPlatformsMsg: $msg")
                    copiedState.message = msg
                }

                _platformState.value = copiedState
            }.launchIn(viewModelScope)
        }
    }

    private fun getGames(token: String) {
        _gameState.value?.let {
            gameJob?.cancel()
            gameJob = getGamesUseCase(
                token,
                it.page,
                it.platformIdFilter
            ).onEach { dataState ->

                val copiedState = it.copy()

                copiedState.isLoading = dataState.isLoading

                dataState.data?.let { games ->
                    Log.i(TAG, "getGames: $games")
                    copiedState.games = games
                }

                dataState.message?.let { msg ->
                    Log.i(TAG, "getGamesMsg: $msg")
                    if (msg == ErrorHandler.EXHAUSTED)
                        copiedState.isExhausted = true

                    copiedState.message = msg
                }

                _gameState.value = copiedState
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