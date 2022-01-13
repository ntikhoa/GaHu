package com.ntikhoa.gahu.business.interactor.game

import com.ntikhoa.gahu.business.datasource.cache.game.GameDao
import com.ntikhoa.gahu.business.datasource.cache.game.GameEntity
import com.ntikhoa.gahu.business.datasource.network.game.GahuGameService
import com.ntikhoa.gahu.business.domain.model.Game
import com.ntikhoa.gahu.business.domain.model.GameDetail
import com.ntikhoa.gahu.business.domain.util.DataState
import com.ntikhoa.gahu.business.interactor.handleUseCaseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetGames(
    private val gameDao: GameDao,
    private val gameService: GahuGameService,
) {
    operator fun invoke(
        token: String,
        page: Int,
        platformId: String?
    ): Flow<DataState<List<Game>>> = flow {

        emit(DataState.loading())

        val gamesEntity = getGamesListCache(page, platformId)
        val gamesCache = gamesEntity.map { it.toDomain() }
        emit(DataState(isLoading = false, data = gamesCache))

        val gamesResponse = gameService.getGames(
            "Bearer $token",
            page,
            platformId
        )

        gamesResponse.data?.let {
            val games = it.games.map { it.toDomain() }

            updateLocalDb(games)

            val gamesUpdated = getGamesListCache(page, platformId).map { it.toDomain() }
            emit(
                DataState.data(
                    message = gamesResponse.message,
                    data = gamesUpdated
                )
            )
        }

    }.catch {
        emit(handleUseCaseException(it))
    }


    private suspend fun getGamesListCache(page: Int, platformId: String?):
            List<GameEntity> {
        return platformId?.let {
            gameDao.getGameListPlatformFilter(page, it)
        } ?: gameDao.getGameList(page)
    }

    private suspend fun updateLocalDb(games: List<GameDetail>) {
        val gameEntities = games.map { it.toGameEntity() }
        val gamePlatformEntities = games.flatMap { it.toGamePlatformEntity() }

        for (gameEntity in gameEntities) {
            gameDao.insertOrReplaceGame(gameEntity)
        }

        for (gamePlatformEntity in gamePlatformEntities) {
            gameDao.insertGamePlatform(gamePlatformEntity)
        }
    }
}