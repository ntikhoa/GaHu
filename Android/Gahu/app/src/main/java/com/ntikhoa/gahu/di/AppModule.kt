package com.ntikhoa.gahu.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ntikhoa.gahu.business.datasource.cache.AppDatabase
import com.ntikhoa.gahu.business.datasource.cache.account.AccountDao
import com.ntikhoa.gahu.business.datasource.datastore.AppDataStore
import com.ntikhoa.gahu.business.datasource.datastore.AppDataStoreImpl
import com.ntikhoa.gahu.business.datasource.network.account.GahuAccountService
import com.ntikhoa.gahu.business.datasource.network.auth.GahuAuthService
import com.ntikhoa.gahu.business.domain.util.Constants
import com.ntikhoa.gahu.presentation.session.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGsonConverter(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideAccountDao(db: AppDatabase): AccountDao {
        return db.getAccountDao()
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofitBuilder: Retrofit.Builder): GahuAuthService {
        return retrofitBuilder.build().create(GahuAuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideAccountService(retrofitBuilder: Retrofit.Builder): GahuAccountService {
        return retrofitBuilder.build().create(GahuAccountService::class.java)
    }

    @Provides
    @Singleton
    fun provideSessionManager(): SessionManager {
        return SessionManager()
    }

    @Provides
    @Singleton
    fun provideDataStore(
        app: Application
    ): AppDataStore {
        return AppDataStoreImpl(app)
    }
}