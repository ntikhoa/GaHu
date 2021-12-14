package com.ntikhoa.gahu.business.interactor.game

import com.ntikhoa.gahu.business.datasource.cache.game.GameDao
import com.ntikhoa.gahu.business.datasource.network.game.GahuGameService
import com.ntikhoa.gahu.business.domain.util.DataState
import com.ntikhoa.gahu.business.interactor.handleUseCaseException
import com.ntikhoa.gahu.presentation.game.GameState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetGames(
    private val gameDao: GameDao,
    private val gameService: GahuGameService,
) {
    operator fun invoke(): Flow<DataState<GameState>> = flow {
        emit(DataState.loading<GameState>())


    }.catch {
        emit(handleUseCaseException(it))
    }
}