package com.ntikhoa.gahu.business.interactor.auth

import com.ntikhoa.gahu.business.datasource.cache.account.AccountDao
import com.ntikhoa.gahu.business.datasource.datastore.AppDataStore
import com.ntikhoa.gahu.business.domain.model.Account
import com.ntikhoa.gahu.business.domain.util.Constants
import com.ntikhoa.gahu.business.domain.util.DataState
import com.ntikhoa.gahu.business.domain.util.SuccessHandler.Companion.LOGOUT_SUCCESSFULLY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Logout(
    private val accountDao: AccountDao,
    private val appDataStore: AppDataStore
) {
    operator fun invoke(): Flow<DataState<Account>> = flow {
        emit(DataState.loading())

        val email = appDataStore.readValue(Constants.DATASTORE_KEY_EMAIL)

        accountDao.deleteByEmail(email)

        appDataStore.clear()

        emit(
            DataState.data(
                message = LOGOUT_SUCCESSFULLY
            )
        )
    }
}