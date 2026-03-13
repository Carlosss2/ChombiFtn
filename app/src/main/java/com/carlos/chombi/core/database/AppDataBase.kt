package com.carlos.chombi.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.carlos.chombi.core.database.dao.BusDao
import com.carlos.chombi.core.database.entities.BusEntity

@Database(entities = [BusEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun busDao(): BusDao
}