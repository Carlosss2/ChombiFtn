package com.carlos.chombi.feauteres.busManagement.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlos.chombi.feauteres.busManagement.domain.entities.Bus
import com.carlos.chombi.feauteres.busManagement.domain.usecases.*
import com.carlos.chombi.feauteres.busManagement.presentation.screens.BusUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusViewModel @Inject constructor(
    private val getAllBusesUseCase: GetAllBusesUseCase,
    private val addBusUseCase: AddBusUseCase,
    private val updateBusUseCase: UpdateBusUseCase,
    private val deleteBusUseCase: DeleteBusUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BusUiState())
    val uiState: StateFlow<BusUiState> = _uiState

    init {
        loadBuses()
    }



    fun loadBuses() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            getAllBusesUseCase()
                .onSuccess { buses ->
                    _uiState.update {
                        it.copy(
                            buses = buses,
                            isLoading = false
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message
                        )
                    }
                }
        }
    }



    fun selectBus(bus: Bus) {
        _uiState.update { it.copy(selectedBus = bus) }
    }



    fun addBus(bus: Bus) {
        viewModelScope.launch {
            addBusUseCase(bus)
                .onSuccess {
                    closeDialogs()
                    loadBuses()
                }
                .onFailure {
                    _uiState.update { it.copy(error = it.error) }
                }
        }
    }

    /* -------------------- UPDATE -------------------- */

    fun updateSelectedBus(updatedBus: Bus) {
        viewModelScope.launch {
            updateBusUseCase(updatedBus)
                .onSuccess {
                    closeDialogs()
                    loadBuses()
                }
                .onFailure {
                    _uiState.update { it.copy(error = it.error) }
                }
        }
    }

    /* -------------------- DELETE -------------------- */

    fun deleteSelectedBus() {
        val bus = _uiState.value.selectedBus ?: return

        viewModelScope.launch {
            deleteBusUseCase(bus)
                .onSuccess {
                    closeDialogs()
                    loadBuses()
                }
                .onFailure {
                    _uiState.update { it.copy(error = it.error) }
                }
        }
    }

    /* -------------------- DIALOGS -------------------- */

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
                showDeleteDialog = false,
                selectedBus = null
            )
        }
    }
}
