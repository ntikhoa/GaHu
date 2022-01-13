package com.ntikhoa.gahu.business.datasource.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ntikhoa.gahu.business.datasource.cache.account.AccountDao
import com.ntikhoa.gahu.business.datasource.cache.account.AccountEntity
import com.ntikhoa.gahu.business.datasource.cache.game.GameDao
import com.ntikhoa.gahu.business.datasource.cache.game.GameEntity
import com.ntikhoa.gahu.business.datasource.cache.game.GamePlatformEntity
import com.ntikhoa.gahu.business.datasource.cache.game.PlatformEntity

@Database(
    entities = [
        AccountEntity::class,
        PlatformEntity::class,
        GameEntity::class,
        GamePlatformEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAccountDao(): AccountDao

    abstract fun getGameDao(): GameDao

    companion object {
        const val DATABASE_NAME = "gahu"
    }
}