package com.carlos.chombi.feauteres.home.domain.repositories

import com.carlos.chombi.feauteres.home.domain.entities.Bus
import kotlinx.coroutines.flow.Flow

interface BusRepository {
    suspend fun getAllBuses(): Result<List<Bus>>

    //Métodos para room
    fun getBuses(): Flow<List<Bus>>
    suspend fun syncBuses()
}