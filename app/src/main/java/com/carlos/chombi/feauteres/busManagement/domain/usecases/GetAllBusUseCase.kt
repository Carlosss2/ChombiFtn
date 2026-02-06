package com.carlos.chombi.feauteres.busManagement.domain.usecases

import com.carlos.chombi.feauteres.busManagement.domain.entities.Bus
import com.carlos.chombi.feauteres.busManagement.domain.repositories.BusRepository

class GetAllBusesUseCase(
    private val repository: BusRepository
) {

    suspend operator fun invoke(): Result<List<Bus>> {
        return try {
            repository.getAllBuses()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
