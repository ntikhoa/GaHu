package com.ntikhoa.gahu.business.interactor.account

import android.util.Log
import com.ntikhoa.gahu.business.datasource.cache.account.AccountDao
import com.ntikhoa.gahu.business.datasource.network.account.GahuAccountService
import com.ntikhoa.gahu.business.domain.model.Account
import com.ntikhoa.gahu.business.domain.util.DataState
import com.ntikhoa.gahu.business.interactor.handleUseCaseException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetAccount(
    private val accountService: GahuAccountService,
    private val accountDao: AccountDao
) {
    operator fun invoke(token: String): Flow<DataState<Account>> = flow {
        emit(DataState.loading())

        val accountResponse = accountService.getUser("Bearer $token")
        accountResponse.data?.let { data ->
            val account = data.toDomain(token)
            val accountEntity = account.toEntity()

            accountDao.insertOrReplace(accountEntity)
            println("network")
            emit(DataState.data(data = account))
        }

    }.catch {
        emit(handleUseCaseException(it))
    }
}