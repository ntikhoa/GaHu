package com.ntikhoa.gahu.business.interactor.auth

import com.ntikhoa.gahu.business.datasource.cache.account.AccountDao
import com.ntikhoa.gahu.business.datasource.datastore.AppDataStore
import com.ntikhoa.gahu.business.domain.model.Account
import com.ntikhoa.gahu.business.domain.util.Constants
import com.ntikhoa.gahu.business.domain.util.DataState
import com.ntikhoa.gahu.business.domain.util.ErrorHandler
import com.ntikhoa.gahu.business.domain.util.SuccessHandler
import com.ntikhoa.gahu.business.interactor.handleUseCaseException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class CheckPrevAuth(
    private val accountDao: AccountDao,
    private val appDataStore: AppDataStore
) {
    operator fun invoke(): Flow<DataState<Account>> = flow {
        emit(DataState.loading())

        delay(2000)

        val email = appDataStore.readValue(Constants.DATASTORE_KEY_EMAIL)
        if (email == ErrorHandler.DATASTORE_VALUE_NOT_FOUND) {
            throw Exception(ErrorHandler.CHECK_PREV_AUTH_FAILED)
        }

        val accountEntity = accountDao.searchByEmail(email)
            ?: throw Exception(ErrorHandler.CHECK_PREV_AUTH_FAILED)

        val account = accountEntity.toDomain()

        emit(
            DataState.data(
                message = SuccessHandler.CHECK_PREV_AUTH_SUCCESSFULLY,
                data = account
            )
        )

    }.catch { e ->
        emit(handleUseCaseException(e))
    }
}