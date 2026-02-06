package com.carlos.chombi.core.navegation


import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationWrapper(
    navGraphs: List<FeatureNavGraph>
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Login
    ) {
        Log.d("NAVIGATION", "NavHost creado")

        navGraphs.forEach { graph ->
            graph.registerGraph(this, navController)
        }
    }
}
