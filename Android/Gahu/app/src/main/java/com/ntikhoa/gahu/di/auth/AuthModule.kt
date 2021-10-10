package com.ntikhoa.gahu.di.auth

import com.ntikhoa.gahu.business.datasource.cache.account.AccountDao
import com.ntikhoa.gahu.business.datasource.datastore.AppDataStore
import com.ntikhoa.gahu.business.datasource.network.auth.GahuAuthService
import com.ntikhoa.gahu.business.interactor.auth.CheckPrevAuth
import com.ntikhoa.gahu.business.interactor.auth.Login
import com.ntikhoa.gahu.business.interactor.auth.Register
import com.ntikhoa.gahu.presentation.session.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
object AuthModule {

    @Provides
    @ActivityRetainedScoped
    fun provideLogin(
        authService: GahuAuthService,
        accountDao: AccountDao,
        appDataStore: AppDataStore
    ): Login {
        return Login(
            authService = authService,
            accountDao = accountDao,
            appDataStore = appDataStore
        )
    }

    @Provides
    @ActivityRetainedScoped
    fun provideRegister(
        authService: GahuAuthService,
        accountDao: AccountDao,
        appDataStore: AppDataStore
    ): Register {
        return Register(
            authService = authService,
            accountDao = accountDao,
            appDataStore = appDataStore
        )
    }

    @Provides
    @ActivityRetainedScoped
    fun provideCheckPrevAuth(
        accountDao: AccountDao,
        appDataStore: AppDataStore
    ): CheckPrevAuth {
        return CheckPrevAuth(
            accountDao = accountDao,
            appDataStore = appDataStore
        )
    }
}