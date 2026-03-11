package com.carlos.chombi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.carlos.chombi.core.navigation.AppNavigatorImpl
import com.carlos.chombi.core.navigation.FeatureNavGraph

import com.carlos.chombi.core.ui.theme.AppTheme
import com.carlos.chombi.feauteres.authentication.navigation.AuthRoutes
import com.carlos.chombi.feauteres.authentication.presentation.screens.LoginScreen
import com.carlos.chombi.feauteres.authentication.presentation.screens.RegisterScreen
import com.carlos.chombi.feauteres.busManagement.presentation.screens.BusScreen
import com.carlos.chombi.feauteres.history.presentation.screens.HistoryScreen
import com.carlos.chombi.feauteres.home.presentation.screens.HomeScreen

import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navGraphs: Set<@JvmSuppressWildcards FeatureNavGraph>

    @Inject
    lateinit var navigator: AppNavigatorImpl


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppTheme {

                val navController = rememberNavController()

                // Se adjunta solo una vez
                LaunchedEffect(navController) {
                    navigator.attach(navController)
                }

                NavHost(
                    navController = navController,
                    startDestination = AuthRoutes.AUTH_GRAPH
                ) {
                    navGraphs.forEach { graph ->
                        graph.register(this)
                    }
                }
            }
        }
    }
}