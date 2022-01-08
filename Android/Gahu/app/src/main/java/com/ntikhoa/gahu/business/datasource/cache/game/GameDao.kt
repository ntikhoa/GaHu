package com.ntikhoa.gahu.business.datasource.cache.game

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface GameDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertOrReplacePlatform(platformEntity: PlatformEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertOrReplaceGame(gameEntity: GameEntity)

    @Query("SELECT * FROM platforms")
    suspend fun getPlatforms(): List<PlatformEntity>?

    @Query("DELETE FROM platforms WHERE pk = :pk")
    suspend fun deleteById(pk: String)

    @Query(
        """
        SELECT * FROM games
        INNER JOIN platforms ON platforms.pk in games.platforms
        LIMIT 10 OFFSET ((:page -1) * 10) 
    """
    )
    suspend fun getGamesList(page: Int): List<GameEntity>

    @Query(
        """
        SELECT * FROM games
        INNER JOIN platforms ON platforms.pk in games.platforms
        WHERE :platformId IN games.platforms       
        LIMIT 10 OFFSET ((:page -1) * 10) 
    """
    )
    suspend fun getGamesListPlatformFilter(page: Int, platformId: String): List<GameEntity>
}