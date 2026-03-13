package com.carlos.chombi.feauteres.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlos.chombi.core.navigation.AppNavigator
import com.carlos.chombi.feauteres.home.domain.entities.Bus
import com.carlos.chombi.feauteres.busManagement.navigation.BusRoutes
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
    private val getAllBusesUseCase: GetAllBusesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    private var timerJob: Job? = null
    private var secondsRemaining = 300 // 5 minutos = 300 segundos

    // Llama a la API, ordena por turno e inicia el día
    fun startDay() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getAllBusesUseCase()
                .onSuccess { buses ->
                    // Ordenar unidades por su turno (shift) de menor a mayor
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
        secondsRemaining = 300 // Reiniciar a 5 minutos
        updateTimerUI()

        timerJob = viewModelScope.launch {
            while (secondsRemaining > 0) {
                delay(1000)
                secondsRemaining--
                updateTimerUI()
            }
            // Si el temporizador llega a 0, cambiamos de unidad automáticamente
            nextUnit()
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

            // Si se llenó la combi, pasar a la siguiente
            if (newCount >= currentState.maxPassengers) {
                nextUnit()
            }
        }
    }

    fun finishLoading() {
        // El chofer decide irse antes
        nextUnit()
    }

    fun skipUnit(busToSkip: Bus) {
        val currentState = _uiState.value
        val currentNextBuses = currentState.nextBuses.toMutableList()

        // Removemos la unidad seleccionada y la mandamos al final de la cola
        currentNextBuses.remove(busToSkip)
        currentNextBuses.add(busToSkip)

        _uiState.update { it.copy(nextBuses = currentNextBuses) }
    }

    private fun nextUnit() {
        val currentState = _uiState.value
        val currentActive = currentState.activeBus

        if (currentActive != null && currentState.nextBuses.isNotEmpty()) {
            val newActive = currentState.nextBuses.first()
            val newNextBuses = currentState.nextBuses.drop(1).toMutableList()

            // El que estaba activo pasa al final de la cola
            newNextBuses.add(currentActive)

            _uiState.update {
                it.copy(
                    activeBus = newActive,
                    nextBuses = newNextBuses,
                    currentPassengers = 0
                )
            }
            startTimer() // Reiniciar reloj para la nueva unidad
        } else {
            // Solo hay 1 unidad registrada, solo reiniciamos la cuenta
            _uiState.update { it.copy(currentPassengers = 0) }
            startTimer()
        }
    }

    fun endDay() {
        timerJob?.cancel()
        _uiState.update { HomeUiState() } // Reinicia todo el estado
    }

    // Funciones de navegación intactas
    fun goHome() { navigator.navigate(HomeRoutes.HOME_GRAPH) }
    fun goToAddBus() { navigator.navigate(BusRoutes.BUS_GRAPH) }
    fun goToHistory() { navigator.navigate(HistoryRoutes.HISTORY_GRAPH) }
}