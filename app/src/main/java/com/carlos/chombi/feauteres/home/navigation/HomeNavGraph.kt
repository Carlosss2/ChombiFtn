package com.carlos.chombi.feauteres.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.carlos.chombi.core.navigation.FeatureNavGraph
import com.carlos.chombi.feauteres.home.presentation.screens.HomeScreen
import javax.inject.Inject

class HomeNavGraph @Inject constructor() : FeatureNavGraph {

    override fun register(builder: NavGraphBuilder) {
        builder.navigation(
            route = HomeRoutes.HOME_GRAPH,
            startDestination = HomeRoutes.HOME
        ) {
            composable(HomeRoutes.HOME) {
                HomeScreen()
            }
        }
    }
}