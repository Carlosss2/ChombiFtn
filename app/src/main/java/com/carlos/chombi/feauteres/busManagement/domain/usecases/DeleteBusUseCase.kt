package com.carlos.chombi.feauteres.busManagement.domain.usecases

import com.carlos.chombi.feauteres.busManagement.domain.entities.Bus
import com.carlos.chombi.feauteres.busManagement.domain.repositories.BusRepository

class DeleteBusUseCase(
    private val repository: BusRepository
) {
    suspend operator fun invoke(bus: Bus): Result<Unit> {
        return try {
            repository.deleteBusById(bus.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
