package com.carlos.chombi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.carlos.chombi.core.di.AppContainer
import com.carlos.chombi.core.ui.theme.AppTheme
import com.carlos.chombi.feauteres.authentication.di.RegisterModule
import com.carlos.chombi.feauteres.authentication.presentation.screens.RegisterScreen

class MainActivity : ComponentActivity() {
    lateinit var appContainer: AppContainer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = AppContainer(this)
        val authMoodule = RegisterModule(appContainer)

        enableEdgeToEdge()
        setContent {
            AppTheme {
                RegisterScreen(authMoodule.privateGetProvideRegisterUserViewModelFactory())
            }
        }
    }
}



