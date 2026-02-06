package com.carlos.chombi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.carlos.chombi.core.di.AppContainer
import com.carlos.chombi.core.navegation.NavigationWrapper
import com.carlos.chombi.core.ui.theme.AppTheme
import com.carlos.chombi.feauteres.authentication.di.AuthModule
import com.carlos.chombi.feauteres.authentication.navigation.AuthNavGraph
import com.carlos.chombi.feauteres.busManagement.di.BusModule
import com.carlos.chombi.feauteres.busManagement.navigation.BusNavGraph

class MainActivity : ComponentActivity() {

    lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appContainer = AppContainer(this)

        val authModule = AuthModule(appContainer)
        val busModule = BusModule(appContainer)

        val navGraphs = listOf(
            AuthNavGraph(authModule),
            BusNavGraph(busModule)
        )

        enableEdgeToEdge()

        setContent {
            AppTheme {
                NavigationWrapper(navGraphs)
            }
        }
    }
}