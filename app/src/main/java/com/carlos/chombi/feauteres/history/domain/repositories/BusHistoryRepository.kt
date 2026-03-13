package com.carlos.chombi.feauteres.history.domain.repositories

import com.carlos.chombi.feauteres.history.domain.entities.BusHistory

interface BusHistoryRepository {
    suspend fun getBusHistory(): Result<List<BusHistory>>
    suspend fun addBusHistory() : Result<Unit>
}
