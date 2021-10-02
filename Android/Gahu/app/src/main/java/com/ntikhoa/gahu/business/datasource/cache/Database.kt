package com.ntikhoa.gahu.business.datasource.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [], version = 1)
abstract class Database : RoomDatabase() {


    companion object {
        const val DATABASE_NAME = "gahu"
    }
}