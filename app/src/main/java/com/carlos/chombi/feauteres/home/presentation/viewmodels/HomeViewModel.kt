package com.carlos.chombi.feauteres.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.carlos.chombi.core.navigation.AppNavigator
import com.carlos.chombi.feauteres.busManagement.navigation.BusRoutes
import com.carlos.chombi.feauteres.history.navigation.HistoryRoutes
import com.carlos.chombi.feauteres.home.navigation.HomeRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigator: AppNavigator,
) : ViewModel(){

    fun goHome() {
        navigator.navigate(HomeRoutes.HOME_GRAPH)
    }

    fun goToAddBus() {
        navigator.navigate(BusRoutes.BUS_GRAPH)
    }

    fun goToHistory() {
        navigator.navigate(HistoryRoutes.HISTORY_GRAPH)
    }
}