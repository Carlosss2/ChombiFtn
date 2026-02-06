package com.carlos.chombi.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable

@Composable
fun NavigationWrapper(
    navGraph: List<FeatureNavGraph>
){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Login,
    ) {
        navGraph.forEach {
            it.registerGraph(this, navController)
        }
    }

}