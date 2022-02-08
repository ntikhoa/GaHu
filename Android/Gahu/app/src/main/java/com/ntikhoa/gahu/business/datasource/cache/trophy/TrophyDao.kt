package com.ntikhoa.gahu.business.datasource.cache.trophy

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface TrophyDao {

    @Query(
        """
        SELECT * FROM trophy_profiles
        WHERE id = :userId
    """
    )
    suspend fun getTrophyProfile(userId: String): TrophyProfileEntity?

    @Query(
        """
        SELECT * FROM trophy_games
        WHERE user_id = :userId
    """
    )
    suspend fun getTrophyGame(userId: String): List<TrophyGameEntity>

    @Insert(onConflict = REPLACE)
    suspend fun insertTrophyProfile(trophyProfile: TrophyProfileEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertTrophyGame(trophyGame: TrophyGameEntity)
}