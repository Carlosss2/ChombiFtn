package com.carlos.chombi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.carlos.chombi.core.di.AppContainer
import com.carlos.chombi.core.ui.theme.AppTheme
import com.carlos.chombi.feauteres.authentication.di.AuthModule
import com.carlos.chombi.feauteres.authentication.presentation.screens.LoginScreen

class MainActivity : ComponentActivity() {
    lateinit var appContainer: AppContainer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = AppContainer(this)
        val authMoodule = AuthModule(appContainer)

        enableEdgeToEdge()
        setContent {
            AppTheme {
                LoginScreen(authMoodule.provideLoginViewModelFactory())
            }
        }
    }
}



