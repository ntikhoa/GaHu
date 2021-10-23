package com.ntikhoa.gahu.business.datasource.cache.game

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface GameDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertOrReplace(platformEntity: PlatformEntity)

    @Query("SELECT * FROM platforms")
    suspend fun getPlatforms(): List<PlatformEntity>
}