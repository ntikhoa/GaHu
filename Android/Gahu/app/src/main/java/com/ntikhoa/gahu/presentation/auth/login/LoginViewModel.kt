package com.ntikhoa.gahu.presentation.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntikhoa.gahu.business.interactor.auth.Login
import com.ntikhoa.gahu.presentation.CancelJob
import com.ntikhoa.gahu.presentation.OnTriggerEvent
import com.ntikhoa.gahu.presentation.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val loginUseCase: Login,
    private val sessionManager: SessionManager
) : ViewModel(), OnTriggerEvent<LoginEvent>, CancelJob {

    private val _state: MutableLiveData<LoginState> = MutableLiveData(LoginState())
    val state: LiveData<LoginState> get() = _state

    private var loginJob: Job? = null

    override fun onTriggerEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> {
                login(event.email, event.password)
            }
        }
    }

    private fun login(email: String, password: String) {
        _state.value?.let {
            loginJob?.cancel()
            loginJob = loginUseCase(email, password)
                .onEach { dataState ->
                    this._state.value = _state.value?.copy(isLoading = dataState.isLoading)

                    dataState.data?.let { account ->
                        sessionManager.token = account.token
                    }

                    dataState.message?.let { message ->
                        _state.value = _state.value?.copy(message = message)
                    }
                }.launchIn(viewModelScope)

        }
    }

    override fun cancelJob() {
        loginJob?.cancel()
        this._state.value = _state.value?.copy(isLoading = false)
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }
}