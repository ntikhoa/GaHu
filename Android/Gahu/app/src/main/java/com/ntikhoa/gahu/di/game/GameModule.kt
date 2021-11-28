package com.ntikhoa.gahu.di.game

import com.ntikhoa.gahu.business.datasource.cache.game.GameDao
import com.ntikhoa.gahu.business.datasource.network.game.GahuGameService
import com.ntikhoa.gahu.business.interactor.game.GetPlatforms
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
object GameModule {

    @Provides
    @ActivityRetainedScoped
    fun provideGetPlatform(
        gameDao: GameDao,
        gameService: GahuGameService
    ): GetPlatforms {
        return GetPlatforms(
            gameDao = gameDao,
            gameService = gameService
        )
    }
}