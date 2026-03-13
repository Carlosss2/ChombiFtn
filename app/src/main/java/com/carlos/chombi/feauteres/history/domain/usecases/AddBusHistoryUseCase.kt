package com.carlos.chombi.feauteres.history.domain.usecases

import com.carlos.chombi.feauteres.history.domain.repositories.BusHistoryRepository
import javax.inject.Inject

class AddBusHistoryUseCase @Inject constructor(
    private val repository: BusHistoryRepository
){
    suspend operator fun invoke() : Result<Unit>{
        return try {
            repository.addBusHistory()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}