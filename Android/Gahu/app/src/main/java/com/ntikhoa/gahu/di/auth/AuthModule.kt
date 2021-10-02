package com.ntikhoa.gahu.di.auth

import com.ntikhoa.gahu.business.datasource.network.auth.GahuAuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthService(retrofitBuilder: Retrofit.Builder): GahuAuthService {
        return retrofitBuilder.build().create(GahuAuthService::class.java)
    }
}