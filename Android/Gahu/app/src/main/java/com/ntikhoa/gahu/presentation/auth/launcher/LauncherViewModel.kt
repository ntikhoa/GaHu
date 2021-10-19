package com.ntikhoa.gahu.presentation.auth.launcher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntikhoa.gahu.business.interactor.auth.CheckPrevAuth
import com.ntikhoa.gahu.presentation.CancelJob
import com.ntikhoa.gahu.presentation.OnTriggerEvent
import com.ntikhoa.gahu.presentation.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LauncherViewModel
@Inject
constructor(
    private val checkPrevAuthUseCase: CheckPrevAuth,
    private val sessionManager: SessionManager
) : ViewModel(),
    OnTriggerEvent<LauncherEvent>,
    CancelJob {

    private val _state: MutableLiveData<String> = MutableLiveData(String())
    val state: LiveData<String> get() = _state

    private var checkPrevAuthJob: Job? = null

    override fun onTriggerEvent(event: LauncherEvent) {
        when (event) {
            is LauncherEvent.CheckPrevAuth -> {
                checkPrevAuth()
            }
        }
    }

    private fun checkPrevAuth() {
        _state.value?.let { state ->
            checkPrevAuthJob?.cancel()
            checkPrevAuthJob = checkPrevAuthUseCase().onEach { dataState ->
                dataState.data?.let { data ->
                    sessionManager.token = data.token
                }

                dataState.message?.let { message ->
                    _state.value = message
                }
            }.launchIn(viewModelScope)
        }
    }

    override fun cancelJob() {
        checkPrevAuthJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }
}