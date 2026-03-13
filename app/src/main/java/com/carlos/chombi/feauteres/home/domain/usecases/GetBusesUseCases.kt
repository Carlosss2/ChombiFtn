package com.carlos.chombi.feauteres.home.domain.usecases

import com.carlos.chombi.feauteres.home.domain.entities.Bus
import com.carlos.chombi.feauteres.home.domain.repositories.BusRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBusesUseCase @Inject constructor(
    private val repository: BusRepository
) {
    operator fun invoke(): Flow<List<Bus>>{
        return repository.getBuses()

    }
}