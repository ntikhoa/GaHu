package com.ntikhoa.gahu.business.interactor.auth

import com.ntikhoa.gahu.business.datasource.cache.account.AccountDao
import com.ntikhoa.gahu.business.datasource.datastore.AppDataStore
import com.ntikhoa.gahu.business.datasource.network.auth.GahuAuthService
import com.ntikhoa.gahu.business.domain.model.Account
import com.ntikhoa.gahu.business.domain.util.Constants
import com.ntikhoa.gahu.business.domain.util.DataState
import com.ntikhoa.gahu.business.domain.util.SuccessHandler
import com.ntikhoa.gahu.business.domain.util.SuccessHandler.Companion.REGISTER_SUCCESSFULLY
import com.ntikhoa.gahu.business.interactor.handleUseCaseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class Register
constructor(
    private val authService: GahuAuthService,
    private val accountDao: AccountDao,
    private val appDataStore: AppDataStore
) {
    operator fun invoke(
        email: String,
        username: String,
        password: String,
        confirmPassword: String
    ): Flow<DataState<Account>> = flow {
        emit(DataState.loading())

        val response = authService.register(email, username, password, confirmPassword)
        response.data?.let { data ->
            val account = data.toDomain()
            val accountEntity = account.toEntity()

            accountDao.insertOrReplace(accountEntity)

            appDataStore.setValue(Constants.DATASTORE_KEY_EMAIL, account.email)

            emit(
                DataState.data(
                    message = REGISTER_SUCCESSFULLY,
                    data = account
                )
            )
        }
    }.catch { e ->
        emit(handleUseCaseException(e))
    }
}