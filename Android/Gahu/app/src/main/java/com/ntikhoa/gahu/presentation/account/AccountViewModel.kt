package com.ntikhoa.gahu.presentation.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntikhoa.gahu.business.domain.model.Account
import com.ntikhoa.gahu.business.domain.util.DataState
import com.ntikhoa.gahu.business.domain.util.SuccessHandler
import com.ntikhoa.gahu.business.domain.util.SuccessHandler.Companion.LOGOUT_SUCCESSFULLY
import com.ntikhoa.gahu.business.interactor.account.GetAccount
import com.ntikhoa.gahu.business.interactor.account.GetAccountCache
import com.ntikhoa.gahu.business.interactor.auth.Logout
import com.ntikhoa.gahu.presentation.CancelJob
import com.ntikhoa.gahu.presentation.OnTriggerEvent
import com.ntikhoa.gahu.presentation.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class AccountViewModel
@Inject
constructor(
    private val getAccountCacheUseCase: GetAccountCache,
    private val getAccountUseCase: GetAccount,
    private val logoutUseCase: Logout,
    private val sessionManager: SessionManager
) : ViewModel(),
    CancelJob,
    OnTriggerEvent<AccountEvent> {

    private var isLoadingCache: Boolean = false
    private var isLoadingNetwork: Boolean = false

    private val _state: MutableLiveData<AccountState> = MutableLiveData(AccountState())
    val state: LiveData<AccountState> get() = _state

    private var logoutJob: Job? = null
    private var getAccountCacheJob: Job? = null
    private var getAccountJob: Job? = null


    override fun onTriggerEvent(event: AccountEvent) {
        when (event) {
            is AccountEvent.Logout -> {
                logout()
            }
            is AccountEvent.GetAccount -> {
                sessionManager.token?.let { token ->

                    isLoadingCache = true
                    isLoadingNetwork = true

                    getAccountCacheJob?.cancel()
                    getAccountCacheJob = getAccountCache {
                        getAccountCacheUseCase(token)
                    }

                    getAccountJob?.cancel()
                    getAccountJob = getAccount {
                        getAccountUseCase(token)
                    }
                }
            }
        }
    }

    private fun checkLoading(): Boolean {
        _state.value?.let { state ->
            println("$isLoadingCache && $isLoadingNetwork")
            return !(!isLoadingCache && !isLoadingNetwork)
//                _state.value = state.copy(isLoading = false)
//                _state.value = state.copy(isLoading = true)
        }
        return false
    }

    private fun getAccount(
        useCase: () -> Flow<DataState<Account>>
    ): Job? {
        return _state.value?.let { state ->
            useCase.invoke().onEach { dataState ->

                isLoadingNetwork = dataState.isLoading
                _state.value = state.copy(isLoading = checkLoading())

                dataState.data?.let { account ->
                    println(account.email)
                    _state.value = state.copy(account = account, isLoading = checkLoading())
//                    println(_state.value)
                }

                dataState.message?.let { message ->
                    println(message)
//                    _state.value = state.copy(message = message)
                }

            }.launchIn(viewModelScope)
        }
    }

    private fun getAccountCache(
        useCase: () -> Flow<DataState<Account>>
    ): Job? {
        return _state.value?.let { state ->
            useCase.invoke().onEach { dataState ->

                isLoadingCache = dataState.isLoading
                _state.value = state.copy(isLoading = checkLoading())

                dataState.data?.let { account ->
                    println(account.email)
                    _state.value = state.copy(account = account, isLoading = checkLoading())
                }

                dataState.message?.let { message ->
                    println(message)
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun logout() {
        _state.value?.let { state ->
            logoutJob?.cancel()
            logoutJob = logoutUseCase()
                .onEach { dataState ->
                    this._state.value = state.copy(isLoading = dataState.isLoading)

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
        getAccountCacheJob?.cancel()
        getAccountJob?.cancel()

        _state.value?.let { state ->
            this._state.value = state.copy(isLoading = false)
        }
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }
}