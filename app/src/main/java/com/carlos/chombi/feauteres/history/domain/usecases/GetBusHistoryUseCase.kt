package com.carlos.chombi.feauteres.history.domain.usecases

import com.carlos.chombi.feauteres.history.domain.entities.BusHistory
import com.carlos.chombi.feauteres.history.domain.repositories.BusHistoryRepository
import javax.inject.Inject

class GetBusHistoryUseCase @Inject constructor(
    private val repository: BusHistoryRepository
) {
    suspend operator fun invoke(): Result<List<BusHistory>> {
        return try {
            repository.getBusHistory()
        } catch (e: Exception) {
            Result.failure(e)
        }

    }
}