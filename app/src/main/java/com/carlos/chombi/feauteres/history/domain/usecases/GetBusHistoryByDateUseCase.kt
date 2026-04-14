package com.carlos.chombi.feauteres.history.domain.usecases

import com.carlos.chombi.feauteres.history.domain.entities.BusHistory
import com.carlos.chombi.feauteres.history.domain.repositories.BusHistoryRepository
import javax.inject.Inject

class GetBusHistoryByDateUseCase @Inject constructor(
    private val repository: BusHistoryRepository
) {
    suspend operator fun invoke(date: String): Result<List<BusHistory>> {
        return try {
            if(date.isBlank()){
                return Result.failure(Exception("La fecha es obligatoria"))
            }
            repository.getBusHistoryByDate(date)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}