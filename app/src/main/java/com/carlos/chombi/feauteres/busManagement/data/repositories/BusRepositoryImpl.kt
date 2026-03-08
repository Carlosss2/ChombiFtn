package com.carlos.chombi.feauteres.busManagement.data.repositories

import com.carlos.chombi.core.network.ChombiApi
import com.carlos.chombi.feauteres.busManagement.data.datasources.remote.mapper.toDto
import com.carlos.chombi.feauteres.busManagement.domain.entities.Bus
import com.carlos.chombi.feauteres.busManagement.domain.repositories.BusRepository
import com.carlos.chombi.feauteres.busManagement.data.datasources.remote.mapper.toDomain
import javax.inject.Inject

class BusRepositoryImpl @Inject constructor(
    private val api: ChombiApi
) : BusRepository {

    override suspend fun getAllBuses(): Result<List<Bus>> {
        return try {
            val response = api.getAllBuses()
            Result.success(response.vehicles.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addBus(bus: Bus): Result<Unit> {
        return try {
            api.addBus(bus.toDto())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateBus(bus: Bus): Result<Unit> {
        return try {
            api.updateBus(
                id = bus.id,
                bus = bus.toDto()
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteBusById(id: Int): Result<Unit> {
        return try {
            api.deleteBus(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}