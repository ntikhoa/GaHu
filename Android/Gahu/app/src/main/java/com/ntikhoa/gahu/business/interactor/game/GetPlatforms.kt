package com.ntikhoa.gahu.business.interactor.game

import android.util.Log
import com.ntikhoa.gahu.business.datasource.cache.game.GameDao
import com.ntikhoa.gahu.business.datasource.network.game.GahuGameService
import com.ntikhoa.gahu.business.domain.model.Platform
import com.ntikhoa.gahu.business.domain.util.DataState
import com.ntikhoa.gahu.business.interactor.handleUseCaseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetPlatforms(
    private val gameDao: GameDao,
    private val gameService: GahuGameService
) {
    /**
     * Get from cache
     * Get from network
     * Insert or replace data in cache
     * Filter Cache and delete invalid data in cache (filter by id)
     */
    operator fun invoke(token: String): Flow<DataState<List<Platform>>> = flow {
        emit(DataState.loading())
        val platformsEntity = gameDao.getPlatforms()

        val platformsCache = platformsEntity.map { it.toDomain() }
        emit(DataState(data = platformsCache, isLoading = true))

        val response = gameService.getPlatforms("Bearer $token")
        response.data?.let { platformsResponse ->
            val platforms = platformsResponse.map { it.toDomain() }

            updateCache(platforms)

            if (platformsCache.isNotEmpty()) {
                deleteInvalidCache(platforms, platformsCache)
            }

            emit(
                DataState.data(
                    data = platforms,
                    message = response.message
                )
            )
        }
    }.catch {
        emit(handleUseCaseException(it))
    }

    private suspend fun updateCache(platforms: List<Platform>) {
        for (platform in platforms) {
            gameDao.insertOrReplacePlatform(platform.toEntity())
        }
    }

    private suspend fun deleteInvalidCache(
        platforms: List<Platform>,
        platformsCache: List<Platform>
    ) {
        val platformsId = platforms.map { it.id }
        val platformsCacheId = platformsCache.map { it.id }

        val filterIds = platformsCacheId.filter {
            platformsId.indexOf(it) == -1
        }

        for (filterId in filterIds) {
            gameDao.deletePlatformById(filterId)
        }
    }
}