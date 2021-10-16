package com.ntikhoa.gahu.business.datasource.datastore

import android.content.Context

interface AppDataStore {
    suspend fun setValue(
        key: String,
        value: String
    )

    suspend fun readValue(key: String): String

    suspend fun clear()
}