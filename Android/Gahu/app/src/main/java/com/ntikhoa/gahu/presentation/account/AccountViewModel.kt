package com.ntikhoa.gahu.presentation.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntikhoa.gahu.business.domain.util.SuccessHandler
import com.ntikhoa.gahu.business.domain.util.SuccessHandler.Companion.LOGOUT_SUCCESSFULLY
import com.ntikhoa.gahu.business.interactor.auth.Logout
import com.ntikhoa.gahu.presentation.CancelJob
import com.ntikhoa.gahu.presentation.OnTriggerEvent
import com.ntikhoa.gahu.presentation.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class AccountViewModel
@Inject
constructor(
    private val logoutUseCase: Logout,
    private val sessionManager: SessionManager
) : ViewModel(),
    CancelJob,
    OnTriggerEvent<AccountEvent> {

    private val _state: MutableLiveData<AccountState> = MutableLiveData(AccountState())
    val state: LiveData<AccountState> get() = _state

    private var logoutJob: Job? = null

    override fun onTriggerEvent(event: AccountEvent) {
        when (event) {
            is AccountEvent.Logout -> {
                logout()
            }
        }
    }

    private fun logout() {
        _state.value?.let { state ->
            logoutJob?.cancel()
            logoutJob = logoutUseCase()
                .onEach { dataState ->
                    this._state.value = state.copy(isLoading = dataState.isLoading)

                    dataState.data?.let {

                    }

                    dataState.message?.let { message ->
                        if (message == LOGOUT_SUCCESSFULLY) {
                            sessionManager.token = null
                        }
                        this._state.value = state.copy(message = message)
                    }
                }.launchIn(viewModelScope)
        }
    }

    override fun cancelJob() {
        logoutJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }
}