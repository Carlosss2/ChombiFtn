package com.carlos.chombi.feauteres.reports.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.carlos.chombi.core.navigation.FeatureNavGraph
import com.carlos.chombi.feauteres.history.navigation.HistoryRoutes
import com.carlos.chombi.feauteres.history.presentation.screens.HistoryScreen
import com.carlos.chombi.feauteres.reports.presentation.screens.ReportsScreen
import javax.inject.Inject

class ReportsNavGraph @Inject constructor(): FeatureNavGraph {
    override fun register(builder: NavGraphBuilder) {
        builder.navigation(
            route = ReportsRoutes.REPORTS_GRAPH,
            startDestination = ReportsRoutes.REPORTS
        ) {
            composable(ReportsRoutes.REPORTS) {
                ReportsScreen()
            }
        }
    }
}