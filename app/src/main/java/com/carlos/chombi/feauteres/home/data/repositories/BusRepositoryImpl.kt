package com.carlos.chombi.feauteres.home.data.repositories

import com.carlos.chombi.core.database.dao.BusDao
import com.carlos.chombi.core.network.ChombiApi
import com.carlos.chombi.feauteres.home.data.datasources.local.mapper.toDomain
import com.carlos.chombi.feauteres.home.data.datasources.remote.mapper.toDomain
import com.carlos.chombi.feauteres.home.data.datasources.remote.mapper.toEntity
import com.carlos.chombi.feauteres.home.domain.entities.Bus
import com.carlos.chombi.feauteres.home.domain.repositories.BusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BusRepositoryImpl @Inject constructor(
    private val api: ChombiApi,
    private val busDao: BusDao
) : BusRepository {

    override suspend fun getAllBuses(): Result<List<Bus>> {
        return try {
            val response = api.getAllBuses()
            Result.success(response.vehicles.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getBuses(): Flow<List<Bus>> {
        return busDao.getAllBuses().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun syncBuses() {
        try {
            val response = api.getAllBuses()
            val entities = response.vehicles.map { it.toEntity() }
            busDao.insertBuses(entities)
        } catch (e: Exception) {
            // Manejar error (ej. log o reportar a la UI si es necesario)
        }
    }
}
