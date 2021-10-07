package com.ntikhoa.gahu.di.auth

import com.ntikhoa.gahu.business.datasource.cache.account.AccountDao
import com.ntikhoa.gahu.business.datasource.network.auth.GahuAuthService
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
        accountDao: AccountDao
    ): Login {
        return Login(
            authService = authService,
            accountDao = accountDao
        )
    }

    @Provides
    @ActivityRetainedScoped
    fun provideRegister(
        authService: GahuAuthService,
        accountDao: AccountDao
    ): Register {
        return Register(
            authService = authService,
            accountDao = accountDao
        )
    }
}