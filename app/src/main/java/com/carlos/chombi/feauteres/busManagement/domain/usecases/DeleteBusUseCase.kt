package com.carlos.chombi.feauteres.busManagement.domain.usecases

import com.carlos.chombi.feauteres.busManagement.domain.entities.Bus
import com.carlos.chombi.feauteres.busManagement.domain.repositories.BusRepository
import javax.inject.Inject

class DeleteBusUseCase @Inject constructor(
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
