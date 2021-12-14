package com.ntikhoa.gahu.business.datasource.cache.game

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface GameDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertOrReplace(platformEntity: PlatformEntity)

    @Query("SELECT * FROM platforms")
    suspend fun getPlatforms(): List<PlatformEntity>?

    @Query("DELETE FROM platforms WHERE pk = :pk")
    suspend fun deleteById(pk: String)

    @Query(
        """
        SELECT * FROM games
        INNER JOIN platforms ON platforms.pk in games.platforms
    """
    )
    suspend fun getGamesList(): List<GameEntity>
}