package com.ntikhoa.gahu.business.interactor.auth

import com.ntikhoa.gahu.business.datasource.cache.account.AccountDao
import com.ntikhoa.gahu.business.datasource.datastore.AppDataStore
import com.ntikhoa.gahu.business.datasource.network.auth.GahuAuthService
import com.ntikhoa.gahu.business.domain.model.Account
import com.ntikhoa.gahu.business.domain.util.Constants
import com.ntikhoa.gahu.business.domain.util.DataState
import com.ntikhoa.gahu.business.domain.util.SuccessHandler
import com.ntikhoa.gahu.business.domain.util.SuccessHandler.Companion.LOGIN_SUCCESSFULLY
import com.ntikhoa.gahu.business.interactor.handleUseCaseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class Login(
    private val authService: GahuAuthService,
    private val accountDao: AccountDao,
    private val appDataStore: AppDataStore
) {
    operator fun invoke(
        email: String,
        password: String
    ): Flow<DataState<Account>> = flow {

        emit(DataState.loading())
        val loginResponse = authService.login(email, password)

        loginResponse.data?.let { data ->

            val account = data.toDomain()

            val accountEntity = account.toEntity()

            accountDao.insertOrReplace(accountEntity)

            appDataStore.setValue(Constants.DATASTORE_KEY_EMAIL, account.email)

            emit(
                DataState.data(
                    message = LOGIN_SUCCESSFULLY,
                    data = account
                )
            )
        }
    }.catch { e ->
        emit(handleUseCaseException(e))
    }
}