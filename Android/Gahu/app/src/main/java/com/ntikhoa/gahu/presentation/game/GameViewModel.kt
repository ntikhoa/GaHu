package com.ntikhoa.gahu.presentation.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val sessionManager: SessionManager
) : ViewModel(), OnTriggerEvent<GameEvent>, CancelJob {
    private val TAG = "GameViewModel"

    private val _state: MutableLiveData<GameState> = MutableLiveData(GameState())
    val state: LiveData<GameState> get() = _state

    var platformJob: Job? = null

    override fun onTriggerEvent(event: GameEvent) {
        when (event) {
            is GameEvent.GetPlatforms -> {
                sessionManager.token?.let { token ->
                    getPlatforms(token)
                }
            }
        }
    }

    private fun getPlatforms(token: String) {
        _state.value?.let {
            platformJob?.cancel()
            platformJob = getPlatformsUseCase(token).onEach { dataState ->
                _state.value = _state.value?.copy(isLoading = dataState.isLoading)
                print("loading: ${dataState.isLoading}")

                dataState.data?.let { platforms ->
                    Log.i(TAG, "getPlatforms: $platforms")
//                    _state.value = _state.value?.copy(platforms = platforms)
                }

                dataState.message?.let { msg ->
                    _state.value = _state.value?.copy(message = msg)
                }
            }.launchIn(viewModelScope)
        }
    }

    override fun cancelJob() {
        platformJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }
}