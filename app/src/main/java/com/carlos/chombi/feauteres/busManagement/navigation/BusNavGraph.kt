package com.carlos.chombi.feauteres.busManagement.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.carlos.chombi.core.navigation.FeatureNavGraph
import com.carlos.chombi.feauteres.busManagement.data.di.BusModule

class BusNavGraph(
    private val busModule: BusModule
) : FeatureNavGraph {

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
    }
}
