package com.ntikhoa.gahu.presentation.auth.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntikhoa.gahu.business.interactor.auth.Login
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val login: Login
) : ViewModel() {

    val state: MutableLiveData<LoginState> = MutableLiveData(LoginState())

    fun onTriggerEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> {
                login(event.email, event.password)
            }
        }
    }

    private fun login(email: String, password: String) {
        //TODO validate data
        state.value?.let { state ->
            login.execute(email, password)
                .onEach { dataState ->
                    println(dataState.isLoading)
                    this.state.value = state.copy(isLoading = dataState.isLoading)

                    dataState.data?.let { account ->
                        println("data: $account")
                    }

                    dataState.message?.let { message ->
                        println("message: $message")
                    }
                }.launchIn(viewModelScope)
        }
    }
}