package com.ntikhoa.gahu.business.interactor.account

import android.util.Log
import com.ntikhoa.gahu.business.datasource.cache.account.AccountDao
import com.ntikhoa.gahu.business.domain.model.Account
import com.ntikhoa.gahu.business.domain.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAccountCache(
    private val accountDao: AccountDao
) {
    operator fun invoke(token: String): Flow<DataState<Account>> = flow {
        val accountEntity = accountDao.getAccount(token)
        accountEntity?.let {
            val account = it.toDomain()
            println("cache")
            emit(DataState.data(data = account))
        }
    }
}