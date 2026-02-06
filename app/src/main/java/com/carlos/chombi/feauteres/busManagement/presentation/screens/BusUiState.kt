package com.carlos.chombi.feauteres.busManagement.presentation.screens

import com.carlos.chombi.feauteres.busManagement.domain.entities.Bus

data class BusUiState(

    val buses: List<Bus> = emptyList(),
    val selectedBus: Bus? = null,
    val isLoading: Boolean = false,
    val error: String? = null,


    val showAddDialog: Boolean = false,
    val showEditDialog: Boolean = false,
    val showDeleteDialog: Boolean = false,


)