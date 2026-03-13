package com.carlos.chombi.core.di

import android.content.Context
import androidx.room.Room
import com.carlos.chombi.core.database.AppDatabase
import com.carlos.chombi.core.database.dao.BusDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "chombi_database"
        ).build()
    }

    @Provides
    fun provideBusDao(appDatabase: AppDatabase): BusDao {
        return appDatabase.busDao()
    }
}
