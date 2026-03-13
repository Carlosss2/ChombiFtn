package com.carlos.chombi.feauteres.home.domain.usecases

data class BusesUseCase (
    val getBuses : GetBusesUseCase,
    val syncBuses : SyncBusesUseCase
)