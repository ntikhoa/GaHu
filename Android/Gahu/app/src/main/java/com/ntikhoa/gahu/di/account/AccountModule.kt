package com.ntikhoa.gahu.di.account

import com.ntikhoa.gahu.business.datasource.cache.account.AccountDao
import com.ntikhoa.gahu.business.datasource.datastore.AppDataStore
import com.ntikhoa.gahu.business.datasource.network.account.GahuAccountService
import com.ntikhoa.gahu.business.interactor.account.GetAccount
import com.ntikhoa.gahu.business.interactor.account.GetAccountCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object AccountModule {

    @Provides
    @ActivityRetainedScoped
    fun providesGetAccount(
        accountService: GahuAccountService,
        accountDao: AccountDao
    ): GetAccount {
        return GetAccount(
            accountService = accountService,
            accountDao = accountDao
        )
    }

    @Provides
    @ActivityRetainedScoped
    fun providesGetAccountCache(
        accountDao: AccountDao
    ): GetAccountCache {
        return GetAccountCache(
            accountDao = accountDao
        )
    }
}