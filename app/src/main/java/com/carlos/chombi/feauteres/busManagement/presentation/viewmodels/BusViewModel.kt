package com.carlos.chombi.feauteres.busManagement.presentation.viewmodels



import androidx.lifecycle.ViewModel
import com.carlos.chombi.feauteres.busManagement.presentation.screens.BusUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class BusViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BusUiState())
    val uiState: StateFlow<BusUiState> = _uiState

    fun openAddDialog() {
        _uiState.update { it.copy(showAddDialog = true) }
    }

    fun openEditDialog() {
        _uiState.update { it.copy(showEditDialog = true) }
    }

    fun openDeleteDialog() {
        _uiState.update { it.copy(showDeleteDialog = true) }
    }

    fun closeDialogs() {
        _uiState.update {
            it.copy(
                showAddDialog = false,
                showEditDialog = false,
                showDeleteDialog = false
            )
        }
    }
}
