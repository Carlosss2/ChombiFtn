package com.carlos.chombi.feauteres.history.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.carlos.chombi.core.navigation.FeatureNavGraph
import com.carlos.chombi.feauteres.history.presentation.screens.HistoryScreen

import javax.inject.Inject

class HistoryNavGraph @Inject constructor(): FeatureNavGraph {
    override fun register(builder: NavGraphBuilder) {
        builder.navigation(
            route = HistoryRoutes.HISTORY_GRAPH,
            startDestination = HistoryRoutes.HISTORY
        ) {
            composable(HistoryRoutes.HISTORY) {
                HistoryScreen()
            }
        }
    }
}