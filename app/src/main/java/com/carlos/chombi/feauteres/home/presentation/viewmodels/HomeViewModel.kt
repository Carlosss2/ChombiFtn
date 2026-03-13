package com.carlos.chombi.feauteres.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlos.chombi.core.navigation.AppNavigator
import com.carlos.chombi.core.hardware.domain.VibratorManager
import com.carlos.chombi.feauteres.home.domain.entities.Bus
import com.carlos.chombi.feauteres.busManagement.navigation.BusRoutes
import com.carlos.chombi.feauteres.history.domain.usecases.AddBusHistoryUseCase
import com.carlos.chombi.feauteres.history.navigation.HistoryRoutes
import com.carlos.chombi.feauteres.home.domain.usecases.GetAllBusesUseCase
import com.carlos.chombi.feauteres.home.navigation.HomeRoutes
import com.carlos.chombi.feauteres.home.presentation.screens.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigator: AppNavigator,
    private val getAllBusesUseCase: GetAllBusesUseCase,
    private val vibratorManager: VibratorManager,
    private val addBusHistoryUseCase: AddBusHistoryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    private var timerJob: Job? = null

    private var secondsRemaining = 120

    fun startDay() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getAllBusesUseCase()
                .onSuccess { buses ->
                    val sortedBuses = buses.sortedBy { it.shift.toIntOrNull() ?: Int.MAX_VALUE }
                    if (sortedBuses.isNotEmpty()) {
                        _uiState.update {
                            it.copy(
                                isDayStarted = true,
                                activeBus = sortedBuses.first(),
                                nextBuses = sortedBuses.drop(1),
                                isLoading = false,
                                currentPassengers = 0
                            )
                        }
                        startTimer()
                    } else {
                        _uiState.update { it.copy(isLoading = false, error = "No hay unidades registradas") }
                    }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(isLoading = false, error = error.message) }
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

            // llego a 0! Inicia vibración de 5 segundos
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
                //
                nextUnit(isManualSkip = true)
            }
        }
    }

    fun finishLoading() {
        // El chofer se va antes, pasa a la siguiente unidad (apaga vibración)
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

        if (isManualSkip) {
            vibratorManager.stop()
        }

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
        }
        timerJob?.cancel()
        vibratorManager.stop() // Detenemos la vibración si cerramos el día
        _uiState.update { HomeUiState() }
    }

    fun goHome() { navigator.navigate(HomeRoutes.HOME_GRAPH) }
    fun goToAddBus() { navigator.navigate(BusRoutes.BUS_GRAPH) }
    fun goToHistory() { navigator.navigate(HistoryRoutes.HISTORY_GRAPH) }
}