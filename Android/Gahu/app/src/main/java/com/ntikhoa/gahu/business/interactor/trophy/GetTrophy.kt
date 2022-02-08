package com.ntikhoa.gahu.business.interactor.trophy

import android.util.Log
import com.ntikhoa.gahu.business.datasource.cache.trophy.TrophyDao
import com.ntikhoa.gahu.business.datasource.network.trophy.GahuTrophyService
import com.ntikhoa.gahu.business.domain.model.TrophyProfile
import com.ntikhoa.gahu.business.domain.util.DataState
import com.ntikhoa.gahu.business.interactor.handleUseCaseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.Exception


class GetTrophy(
    private val trophyService: GahuTrophyService,
    private val trophyDao: TrophyDao
) {
    private val TAG = "GetTrophy"

    operator fun invoke(token: String, userId: String): Flow<DataState<TrophyProfile>> = flow {
        emit(DataState.loading())

        val trophyCache = getTrophyCache(userId)
        emit(DataState(isLoading = true, data = trophyCache))

        val response = trophyService.getTrophies("Bearer $token")
        val data = response.data
        data?.let {
            val trophy = it.toDomain()

            if (trophyCache != trophy) {
                updateTrophyCache(trophy)
            }

            val trophyUpdated = getTrophyCache(userId)
            emit(
                DataState.data(
                    data = trophyUpdated,
                    message = response.message,
                )
            )
        }

    }.catch {
        emit(handleUseCaseException(it))
    }

    private suspend fun updateTrophyCache(
        trophyResponse: TrophyProfile
    ) {
        val trophyProfileEntity = trophyResponse.toTrophyProfileEntity()
        val trophyGameEntities = trophyResponse.toTrophyGameEntities()
        trophyDao.insertTrophyProfile(trophyProfileEntity)
        for (trophyGameEntity in trophyGameEntities) {
            trophyDao.insertTrophyGame(trophyGameEntity)
        }
    }


    private suspend fun getTrophyCache(userId: String): TrophyProfile? {
        val trophyProfileEntity = trophyDao.getTrophyProfile(userId)
        return trophyProfileEntity?.let {
            val trophyGameEntity = trophyDao.getTrophyGame(userId)
            TrophyProfile.fromEntityMap(it, trophyGameEntity)
        }
    }
}