package com.carlos.chombi.feauteres.home.domain.usecases

import com.carlos.chombi.feauteres.home.domain.repositories.BusRepository
import javax.inject.Inject

class SyncBusesUseCase @Inject constructor(
    private val repository: BusRepository

){
    suspend operator fun invoke(){
        repository.syncBuses()
    }

}