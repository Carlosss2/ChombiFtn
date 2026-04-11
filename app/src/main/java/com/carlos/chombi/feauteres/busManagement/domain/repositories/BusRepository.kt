package com.carlos.chombi.feauteres.busManagement.domain.repositories

import com.carlos.chombi.feauteres.busManagement.domain.entities.Bus
interface BusRepository {
    suspend fun addBus(bus: Bus): Result<Unit>
    suspend fun deleteBusById(id: String): Result<Unit>
    suspend fun updateBus(bus: Bus): Result<Unit>
    suspend fun getAllBuses(): Result<List<Bus>>
    suspend fun getBusByUnitNumber(unitNumber: Int): Result<Bus>
}