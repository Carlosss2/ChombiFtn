package com.carlos.chombi.feauteres.history.data.repositories

import com.carlos.chombi.core.network.ChombiApi
import com.carlos.chombi.feauteres.history.data.datasources.remote.mapper.toDomain
import com.carlos.chombi.feauteres.history.domain.entities.BusHistory
import com.carlos.chombi.feauteres.history.domain.repositories.BusHistoryRepository
import javax.inject.Inject

class BusHistoryRepositoryImpl @Inject constructor(
    private val api: ChombiApi
) : BusHistoryRepository{
    override suspend fun getBusHistory(): Result<List<BusHistory>> {

        return try {
            val response = api.getBusHistory()
            Result.success(response.history.toDomain())
        } catch (e: Exception){
            Result.failure(e)
        }
    }
    override suspend fun addBusHistory(): Result<Unit>{
        return try {
            api.addBusHistory()
            Result.success(Unit)
        } catch (e: Exception){
            Result.failure(e)
        }

    }

}