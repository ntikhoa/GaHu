package com.ntikhoa.gahu.business.interactor.auth

import com.ntikhoa.gahu.business.datasource.cache.account.AccountDao
import com.ntikhoa.gahu.business.datasource.cache.account.AccountEntity
import com.ntikhoa.gahu.business.datasource.network.auth.GahuAuthService
import com.ntikhoa.gahu.business.domain.model.Account
import com.ntikhoa.gahu.business.domain.util.DataState
import com.ntikhoa.gahu.business.interactor.handleUseCaseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class Login(
    private val authService: GahuAuthService,
    private val accountDao: AccountDao,

    ) {
    fun execute(
        email: String,
        password: String
    ): Flow<DataState<Account>> = flow {

        emit(DataState.loading())
        val loginResponse = authService.login(email, password)

        loginResponse.data?.let { data ->

            val account = data.toDomain()

            val accountEntity = account.toEntity()

            accountDao.insertOrReplace(accountEntity)

            val result = accountDao.searchByPk(account.id)
            println("localdb: $result")

            emit(
                DataState.data(
                    message = loginResponse.message,
                    data = account
                )
            )
        }
    }.catch { e ->
        println("Exception throw")
        emit(handleUseCaseException(e))
    }
}