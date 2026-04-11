package com.carlos.chombi.feauteres.history.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlos.chombi.core.navigation.AppNavigator
import com.carlos.chombi.feauteres.busManagement.navigation.BusRoutes
import com.carlos.chombi.feauteres.history.domain.usecases.GetBusHistoryUseCase
import com.carlos.chombi.feauteres.history.navigation.HistoryRoutes
import com.carlos.chombi.feauteres.history.presentation.screens.BusHistoryUiState
import com.carlos.chombi.feauteres.home.navigation.HomeRoutes
import com.carlos.chombi.feauteres.reports.navigation.ReportsRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val navigator: AppNavigator,
    private val getBusHistoryUseCase: GetBusHistoryUseCase
): ViewModel(){

    private val _uiState = MutableStateFlow(BusHistoryUiState())
    val uiState : StateFlow<BusHistoryUiState> = _uiState


    init {
        loadHistoryBuses()
    }

    fun loadHistoryBuses() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getBusHistoryUseCase().onSuccess {
                buses -> _uiState.update { it.copy(buses = buses, isLoading = false) }
            }
            .onFailure { error ->
                _uiState.update { it.copy(isLoading = false, error = error.message) }}
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