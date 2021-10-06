package com.ntikhoa.gahu.presentation.auth.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntikhoa.gahu.business.domain.util.Constants
import com.ntikhoa.gahu.business.interactor.auth.Login
import com.ntikhoa.gahu.presentation.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val login: Login,
    private val sessionManager: SessionManager
) : ViewModel() {

    val state: MutableLiveData<LoginState> = MutableLiveData(LoginState())

    private var loginJob: Job? = null

    fun onTriggerEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> {
                login(event.email, event.password)
            }
        }
    }

    private fun login(email: String, password: String) {
        try {
            loginJob?.cancel()
            validateLoginInput(email, password)
            state.value?.let { state ->
                loginJob = login.execute(email, password)
                    .onEach { dataState ->
                        this.state.value = state.copy(isLoading = dataState.isLoading)

                        dataState.data?.let { account ->
                            println("dataState: ${account.token}")
                            sessionManager.token = "Bearer ${account.token}"
                        }

                        dataState.message?.let { message ->
                            this.state.value = state.copy(message = message)
                        }
                    }.launchIn(viewModelScope)

                loginJob?.invokeOnCompletion {
                    println("login job cancelled")
                }

            }
        } catch (e: Exception) {
            println(e.message ?: Constants.UNKNOWN_ERROR)
        }
    }

    private fun validateLoginInput(email: String, password: String) {
        if (email.isBlank()) {
            throw Exception("Email cannot be blank")
        }
        if (password.isBlank()) {
            throw Exception("Password cannot be blank")
        }
    }

    fun cancelJob() {
        loginJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        loginJob?.cancel()
    }
}