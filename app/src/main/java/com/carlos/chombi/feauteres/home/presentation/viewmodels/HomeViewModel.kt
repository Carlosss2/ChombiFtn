package com.carlos.chombi.feauteres.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlos.chombi.core.navigation.AppNavigator
import com.carlos.chombi.core.hardware.domain.VibratorManager
import com.carlos.chombi.feauteres.home.domain.entities.Bus
import com.carlos.chombi.feauteres.busManagement.navigation.BusRoutes
import com.carlos.chombi.feauteres.history.domain.usecases.AddBusHistoryUseCase
import com.carlos.chombi.feauteres.history.navigation.HistoryRoutes
import com.carlos.chombi.feauteres.home.domain.usecases.GetBusesUseCase
import com.carlos.chombi.feauteres.home.domain.usecases.SyncBusesUseCase
import com.carlos.chombi.feauteres.home.navigation.HomeRoutes
import com.carlos.chombi.feauteres.home.presentation.screens.HomeUiState
import com.carlos.chombi.feauteres.reports.navigation.ReportsRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigator: AppNavigator,
    private val getBusesUseCase: GetBusesUseCase,
    private val syncBusesUseCase: SyncBusesUseCase,
    private val vibratorManager: VibratorManager,
    private val addBusHistoryUseCase: AddBusHistoryUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    private var timerJob: Job? = null
    private var secondsRemaining = 120

    init {
        observeBuses()
    }

    private fun observeBuses() {
        viewModelScope.launch {
            getBusesUseCase().collectLatest { buses ->
                if (buses.isNotEmpty()) {

                    val sortedBuses = buses.sortedBy { it.shift.toIntOrNull() ?: Int.MAX_VALUE }

                    _uiState.update { state ->
                        state.copy(
                            isDayStarted = true,
                            activeBus = sortedBuses.first(),
                            nextBuses = sortedBuses.drop(1),
                            isLoading = false
                        )
                    }

                    // Si el timer no está corriendo, lo iniciamos (al volver de navegación)
                    if (timerJob == null) {
                        startTimer()
                    }
                } else {
                    // Si Room está vacío, mostramos la pantalla de "Iniciar Día"
                    _uiState.update { it.copy(isDayStarted = false, isLoading = false) }
                }
            }
        }
    }


    fun startDay() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                // El repositorio hace el fetch y guarda en Room
                syncBusesUseCase()
                // No necesitamos actualizar el estado manualmente aquí,
                // porque observeBuses() detectará el cambio en Room.
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Error al conectar: ${e.message}") }
            }
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        secondsRemaining = 120
        updateTimerUI()

        timerJob = viewModelScope.launch {
            while (secondsRemaining > 0) {
                delay(1000)
                secondsRemaining--
                updateTimerUI()
            }
            // Llegó a 0! Vibración y cambio de unidad
            vibratorManager.vibrate(5000L)
            nextUnit(isManualSkip = false)
        }
    }

    private fun updateTimerUI() {
        val minutes = secondsRemaining / 60
        val seconds = secondsRemaining % 60
        val timeString = String.format("%02d:%02d", minutes, seconds)
        _uiState.update { it.copy(timeRemaining = timeString) }
    }

    fun addPassenger() {
        val currentState = _uiState.value
        if (currentState.currentPassengers < currentState.maxPassengers) {
            val newCount = currentState.currentPassengers + 1
            _uiState.update { it.copy(currentPassengers = newCount) }

            if (newCount >= currentState.maxPassengers) {
                nextUnit(isManualSkip = true)
            }
        }
    }

    fun finishLoading() {
        nextUnit(isManualSkip = true)
    }

    fun skipUnit(busToSkip: Bus) {
        val currentState = _uiState.value
        val currentNextBuses = currentState.nextBuses.toMutableList()

        currentNextBuses.remove(busToSkip)
        currentNextBuses.add(busToSkip)

        _uiState.update { it.copy(nextBuses = currentNextBuses) }
    }

    private fun nextUnit(isManualSkip: Boolean = false) {
        if (isManualSkip) vibratorManager.stop()

        val currentState = _uiState.value
        val currentActive = currentState.activeBus

        if (currentActive != null && currentState.nextBuses.isNotEmpty()) {
            val newActive = currentState.nextBuses.first()
            val newNextBuses = currentState.nextBuses.drop(1).toMutableList()

            newNextBuses.add(currentActive)

            _uiState.update {
                it.copy(
                    activeBus = newActive,
                    nextBuses = newNextBuses,
                    currentPassengers = 0
                )
            }
            startTimer()
        } else {
            _uiState.update { it.copy(currentPassengers = 0) }
            startTimer()
        }
    }

    fun endDay() {
        viewModelScope.launch {
            addBusHistoryUseCase()
            // Aquí deberías llamar a un UseCase que ejecute busDao.clearAll()
            // clearBusesUseCase()

            timerJob?.cancel()
            timerJob = null
            vibratorManager.stop()
            _uiState.update { HomeUiState() } // Reset de la UI
        }
    }

    fun goHome() { navigator.navigate(HomeRoutes.HOME_GRAPH) }
    fun goToAddBus() { navigator.navigate(BusRoutes.BUS_GRAPH) }
    fun goToHistory() { navigator.navigate(HistoryRoutes.HISTORY_GRAPH) }
    fun goToReports(){navigator.navigate(ReportsRoutes.REPORTS_GRAPH)}
}