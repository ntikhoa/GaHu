package com.ntikhoa.gahu.di.trophy

import com.ntikhoa.gahu.business.datasource.network.trophy.GahuTrophyService
import com.ntikhoa.gahu.business.interactor.trophy.GetTrophy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped


@InstallIn(ActivityRetainedComponent::class)
@Module
object TrophyModule {

    @Provides
    @ActivityRetainedScoped
    fun providesGetTrophyUseCase(trophyService: GahuTrophyService): GetTrophy {
        return GetTrophy(trophyService)
    }
}