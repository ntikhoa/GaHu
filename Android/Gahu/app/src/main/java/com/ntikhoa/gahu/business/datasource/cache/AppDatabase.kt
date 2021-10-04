package com.ntikhoa.gahu.business.datasource.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ntikhoa.gahu.business.datasource.cache.account.AccountDao
import com.ntikhoa.gahu.business.datasource.cache.account.AccountEntity

@Database(entities = [AccountEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAccountDao(): AccountDao

    companion object {
        const val DATABASE_NAME = "gahu"
    }
}