package com.carlos.chombi.feauteres.home.domain.repositories

import com.carlos.chombi.feauteres.home.domain.entities.Bus

interface BusRepository {
    suspend fun getAllBuses(): Result<List<Bus>>
}