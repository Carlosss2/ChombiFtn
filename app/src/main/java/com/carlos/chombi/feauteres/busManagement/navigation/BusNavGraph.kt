package com.carlos.chombi.feauteres.busManagement.navigation

import androidx.navigation.NavGraphBuilder

import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.carlos.chombi.core.navigation.FeatureNavGraph
import com.carlos.chombi.feauteres.busManagement.presentation.screens.BusScreen
import javax.inject.Inject

class BusNavGraph @Inject constructor() : FeatureNavGraph {

    override fun register(builder: NavGraphBuilder) {
        builder.navigation(
            route = BusRoutes.BUS_GRAPH,
            startDestination = BusRoutes.BUS
        ) {
            composable(BusRoutes.BUS) {
                BusScreen()
            }
        }
    }
}
