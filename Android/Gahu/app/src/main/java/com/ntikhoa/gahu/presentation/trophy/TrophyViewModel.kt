package com.ntikhoa.gahu.presentation.trophy

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntikhoa.gahu.business.interactor.trophy.GetTrophy
import com.ntikhoa.gahu.presentation.CancelJob
import com.ntikhoa.gahu.presentation.OnTriggerEvent
import com.ntikhoa.gahu.presentation.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TrophyViewModel
@Inject
constructor(
    private val getTrophyUseCase: GetTrophy,
    private val session: SessionManager
) : ViewModel(), OnTriggerEvent<TrophyEvent>, CancelJob {

    private val TAG = "TrophyViewModel"

    private val _state = MutableLiveData(TrophyState())
    val state: LiveData<TrophyState> get() = _state

    private var trophyJob: Job? = null

    override fun onTriggerEvent(event: TrophyEvent) {
        when (event) {
            is TrophyEvent.GetTrophy -> {
                session.token?.let {
                    getTrophy(it)
                }
            }
        }
    }

    private fun getTrophy(token: String) {
        _state.value?.let {
            trophyJob?.cancel()
            trophyJob = getTrophyUseCase(token).onEach { dataState ->

                val copiedState = it.copy()

                copiedState.isLoading = dataState.isLoading

                dataState.data?.let { trophy ->
                    Log.i(TAG, "getTrophy: $trophy")
                    copiedState.trophyProfile = trophy
                }

                dataState.message?.let { msg ->
                    Log.i(TAG, "getTrophy: $msg")
                    copiedState.message = msg
                }

                _state.value = copiedState

            }.launchIn(viewModelScope)
        }
    }

    override fun cancelJob() {
        trophyJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }
}