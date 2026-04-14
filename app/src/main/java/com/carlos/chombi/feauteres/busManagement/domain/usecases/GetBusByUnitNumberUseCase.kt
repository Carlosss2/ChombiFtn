package com.carlos.chombi.feauteres.busManagement.domain.usecases

import com.carlos.chombi.feauteres.busManagement.domain.entities.Bus
import com.carlos.chombi.feauteres.busManagement.domain.repositories.BusRepository
import javax.inject.Inject

class GetBusByUnitNumberUseCase @Inject constructor(
    private val repository: BusRepository
) {
    suspend operator fun invoke(unitNumber: Int): Result<Bus> {
        return try {
            if (unitNumber <= 0) {
                return Result.failure(Exception("El número de unidad no es válido"))
            }
            repository.getBusByUnitNumber(unitNumber)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}