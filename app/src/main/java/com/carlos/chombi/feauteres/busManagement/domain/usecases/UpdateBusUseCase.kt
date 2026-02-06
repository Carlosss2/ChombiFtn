package com.carlos.chombi.feauteres.busManagement.domain.usecases

import com.carlos.chombi.feauteres.busManagement.domain.entities.Bus
import com.carlos.chombi.feauteres.busManagement.domain.repositories.BusRepository

class UpdateBusUseCase(
    private val repository: BusRepository
) {
    suspend operator fun invoke(bus: Bus): Result<Unit> {
        return try {
            if (bus.licencePlate.isBlank())
                return Result.failure(Exception("La placa es obligatoria"))

            if (bus.driver.isBlank())
                return Result.failure(Exception("El conductor es obligatorio"))

            if (bus.unitNumber <= 0)
                return Result.failure(Exception("Unidad inválida"))

            repository.updateBus(bus)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
