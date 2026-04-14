package com.carlos.chombi.feauteres.busManagement.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlos.chombi.core.hardware.domain.CameraManager
import com.carlos.chombi.core.navigation.AppNavigator
import com.carlos.chombi.feauteres.busManagement.domain.entities.Bus
import com.carlos.chombi.feauteres.busManagement.domain.usecases.*
import com.carlos.chombi.feauteres.busManagement.navigation.BusRoutes
import com.carlos.chombi.feauteres.busManagement.presentation.screens.BusUiState
import com.carlos.chombi.feauteres.history.navigation.HistoryRoutes
import com.carlos.chombi.feauteres.home.navigation.HomeRoutes
import com.carlos.chombi.feauteres.reports.navigation.ReportsRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class BusViewModel @Inject constructor(
    private val getAllBusesUseCase: GetAllBusesUseCase,
    private val addBusUseCase: AddBusUseCase,
    private val updateBusUseCase: UpdateBusUseCase,
    private val deleteBusUseCase: DeleteBusUseCase,
    private val getBusByUnitNumberUseCase: GetBusByUnitNumberUseCase,
    private val navigator: AppNavigator,
    private val cameraManager: CameraManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(BusUiState())
    val uiState: StateFlow<BusUiState> = _uiState

    private val _currentPhoto = MutableStateFlow<File?>(null)
    val currentPhoto: StateFlow<File?> = _currentPhoto

    init {
        loadBuses()
    }

    fun loadBuses() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getAllBusesUseCase()
                .onSuccess { buses ->
                    _uiState.update { it.copy(buses = buses, isLoading = false) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(isLoading = false, error = error.message) }
                }
        }
    }

    fun searchBusByUnit(unitNumber: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getBusByUnitNumberUseCase(unitNumber)
                .onSuccess { bus ->

                    _uiState.update { it.copy(buses = listOf(bus), isLoading = false) }
                }
                .onFailure { error ->
                    // Si no se encuentra, dejamos la lista vacía
                    _uiState.update { it.copy(buses = emptyList(), isLoading = false, error = error.message) }
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
                    _uiState.update { state -> state.copy(error = it.message) }
                }
        }
    }

    fun updateSelectedBus(updatedBus: Bus) {
        viewModelScope.launch {
            updateBusUseCase(updatedBus)
                .onSuccess {
                    closeDialogs()
                    loadBuses()
                }
                .onFailure {
                    _uiState.update { state -> state.copy(error = it.message) }
                }
        }
    }

    fun deleteSelectedBus() {
        val bus = _uiState.value.selectedBus ?: return
        viewModelScope.launch {
            deleteBusUseCase(bus)
                .onSuccess {
                    closeDialogs()
                    loadBuses()
                }
                .onFailure {
                    _uiState.update { state -> state.copy(error = it.message) }
                }
        }
    }

    fun takePhoto() {
        viewModelScope.launch {
            cameraManager.takePhoto()
                .onSuccess { file ->
                    _currentPhoto.value = file
                }
                .onFailure { error ->
                    _uiState.update { it.copy(error = "Error al tomar foto: ${error.message}") }
                }
        }
    }

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
        _currentPhoto.value = null
        _uiState.update {
            it.copy(
                showAddDialog = false,
                showEditDialog = false,
                showDeleteDialog = false,
                selectedBus = null
            )
        }
    }

    fun goHome() {
        navigator.navigate(HomeRoutes.HOME_GRAPH)
    }

    fun goToAddBus() {
        navigator.navigate(BusRoutes.BUS_GRAPH)
    }

    fun goToHistory() {
        navigator.navigate(HistoryRoutes.HISTORY_GRAPH)
    }

    fun goToReports(){
        navigator.navigate(ReportsRoutes.REPORTS_GRAPH)
    }
}