package com.ntikhoa.gahu.business.datasource.cache.game

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface GameDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertOrReplacePlatform(platformEntity: PlatformEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertOrReplaceGame(gameEntity: GameEntity)

    @Insert(onConflict = IGNORE)
    suspend fun insertGamePlatform(gamePlatformEntity: GamePlatformEntity)

    @Query("SELECT * FROM platforms")
    suspend fun getPlatforms(): List<PlatformEntity>?

    @Query("DELETE FROM platforms WHERE _pk = :pk")
    suspend fun deletePlatformById(pk: String)

    @Query(
        """
        SELECT * FROM games
        LIMIT (:page * 10)
    """
    )
    suspend fun getGameList(page: Int): List<GameEntity>

    @Query(
        """
         SELECT * FROM games
         JOIN game_platform ON game_id = pk
         WHERE platform_id = :platformId
         LIMIT (:page * 10)
    """
    )
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    suspend fun getGameListPlatformFilter(page: Int, platformId: String): List<GameEntity>
}