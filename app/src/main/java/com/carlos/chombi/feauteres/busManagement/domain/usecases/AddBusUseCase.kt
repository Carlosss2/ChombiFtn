package com.carlos.chombi.feauteres.busManagement.domain.usecases

import com.carlos.chombi.feauteres.busManagement.domain.entities.Bus
import com.carlos.chombi.feauteres.busManagement.domain.repositories.BusRepository
import javax.inject.Inject

class AddBusUseCase @Inject constructor(
    private val repository: BusRepository
) {
    suspend operator fun invoke(bus: Bus): Result<Unit> {
        return try {
            // Validaciones de negocio
            if (bus.licencePlate.isBlank()) {
                return Result.failure(Exception("La placa es obligatoria."))
            }
            if (bus.unitNumber <= 0) {
                return Result.failure(Exception("El número de unidad debe ser mayor a 0."))
            }
            if (bus.driver.isBlank()) {
                return Result.failure(Exception("El nombre del conductor es obligatorio."))
            }
            if (bus.model.isBlank()) {
                return Result.failure(Exception("El modelo es obligatorio."))
            }
            if (bus.imageUrl.isNullOrBlank()) {
                return Result.failure(Exception("La fotografía es obligatoria."))
            }

            // Llamada al repositorio
            repository.addBus(bus)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}