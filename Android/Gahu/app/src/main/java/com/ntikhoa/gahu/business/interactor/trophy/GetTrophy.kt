package com.ntikhoa.gahu.business.interactor.trophy

import com.ntikhoa.gahu.business.datasource.network.trophy.GahuTrophyService
import com.ntikhoa.gahu.business.domain.model.Trophy
import com.ntikhoa.gahu.business.domain.util.DataState
import com.ntikhoa.gahu.business.interactor.handleUseCaseException
import com.ntikhoa.gahu.presentation.trophy.TrophyState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow


class GetTrophy(
    private val trophyService: GahuTrophyService
) {
    operator fun invoke(token: String): Flow<DataState<Trophy>> = flow {
        emit(DataState.loading())
        val response = trophyService.getTrophies("Bearer $token")
        val data = response.data
        data?.let {
            val trophy = it.toDomain()
            emit(
                DataState.data(
                    data = trophy,
                    message = response.message,
                )
            )
        }

    }.catch {
        emit(handleUseCaseException(it))
    }
}