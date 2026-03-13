package com.carlos.chombi.feauteres.home.presentation.screens

import com.carlos.chombi.feauteres.home.domain.entities.Bus

data class HomeUiState(
    val isDayStarted: Boolean = false,
    val activeBus: Bus? = null,
    val nextBuses: List<Bus> = emptyList(),
    val currentPassengers: Int = 0,
    val maxPassengers: Int = 14,
    val timeRemaining: String = "05:00",
    val isLoading: Boolean = false,
    val error: String? = null
)