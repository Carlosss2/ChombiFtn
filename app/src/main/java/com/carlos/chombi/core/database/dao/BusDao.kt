package com.carlos.chombi.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.carlos.chombi.core.database.entities.BusEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BusDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBuses(vehicles: List<BusEntity>)

    @Query("SELECT * FROM bus_table")
    fun getAllBuses(): Flow<List<BusEntity>>

    @Query("DELETE FROM bus_table")
    suspend fun clearBuses()
}
