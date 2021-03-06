package com.ntikhoa.gahu.presentation.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntikhoa.gahu.business.interactor.auth.Register
import com.ntikhoa.gahu.presentation.CancelJob
import com.ntikhoa.gahu.presentation.OnTriggerEvent
import com.ntikhoa.gahu.presentation.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject
constructor(
    private val registerUseCase: Register,
    private val sessionManager: SessionManager
) : ViewModel(),
    OnTriggerEvent<RegisterEvent>,
    CancelJob {

    private val _state: MutableLiveData<RegisterState> = MutableLiveData(RegisterState())
    val state: LiveData<RegisterState> get() = _state

    private var registerJob: Job? = null

    override fun onTriggerEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.Register -> {
                event.apply {
                    register(email, username, password, confirmPassword)
                }
            }
        }
    }

    private fun register(
        email: String,
        username: String,
        password: String,
        confirmPassword: String
    ) {
        _state.value?.let {
            registerJob?.cancel()
            registerJob = registerUseCase(
                email,
                username,
                password,
                confirmPassword
            ).onEach { dataState ->
                val copiedState = it.copy()

                copiedState.isLoading = dataState.isLoading

                dataState.data?.let { data ->
                    sessionManager.token = data.token
                }

                dataState.message?.let { message ->
                    copiedState.message = message
                }

                _state.value = copiedState

            }.launchIn(viewModelScope)
        }
    }

    override fun cancelJob() {
        registerJob?.cancel()
        this._state.value = _state.value?.copy(isLoading = false)
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }
}