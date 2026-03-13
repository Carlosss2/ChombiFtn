package com.carlos.chombi.feauteres.history.presentation.screens

import com.carlos.chombi.feauteres.history.domain.entities.BusHistory

data class BusHistoryUiState(
    val buses: List<BusHistory> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)