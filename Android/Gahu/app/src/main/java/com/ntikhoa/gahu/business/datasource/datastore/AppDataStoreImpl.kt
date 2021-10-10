package com.ntikhoa.gahu.business.datasource.datastore

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ntikhoa.gahu.business.domain.util.Constants
import com.ntikhoa.gahu.business.domain.util.ErrorHandler
import kotlinx.coroutines.flow.first

class AppDataStoreImpl(
    private val context: Context
) : AppDataStore {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(Constants.DATASTORE_NAME)

    override suspend fun setValue(key: String, value: String) {
        context.dataStore.edit {
            it[stringPreferencesKey(key)] = value
        }
    }

    override suspend fun readValue(key: String): String {
        return context.dataStore.data.first()[stringPreferencesKey(key)]
            ?: ErrorHandler.DATASTORE_VALUE_NOT_FOUND
    }
}
