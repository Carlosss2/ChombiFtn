package com.carlos.chombi.feauteres.busManagement.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.carlos.chombi.core.navegation.Bus
import com.carlos.chombi.core.navegation.FeatureNavGraph
import com.carlos.chombi.feauteres.busManagement.di.BusModule
import com.carlos.chombi.feauteres.busManagement.presentation.screens.BusScreen
import com.carlos.chombi.feauteres.busManagement.presentation.viewmodels.BusViewModel

class BusNavGraph(
    private val busModule: BusModule
) : FeatureNavGraph {

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {

        navGraphBuilder.composable<Bus> {

            val viewModel: BusViewModel = viewModel(
                factory = busModule.provideBusViewModelFactory()
            )

            BusScreen(
                factory = busModule.provideBusViewModelFactory()
            )
        }
    }
}
