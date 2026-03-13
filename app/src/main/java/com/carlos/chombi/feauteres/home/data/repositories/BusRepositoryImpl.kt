package com.carlos.chombi.feauteres.home.data.repositories

import com.carlos.chombi.core.network.ChombiApi
import com.carlos.chombi.feauteres.home.data.datasources.remote.mapper.toDomain
import com.carlos.chombi.feauteres.home.domain.entities.Bus
import com.carlos.chombi.feauteres.home.domain.repositories.BusRepository
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

}