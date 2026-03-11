package com.carlos.chombi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import com.carlos.chombi.core.ui.theme.AppTheme
import com.carlos.chombi.feauteres.authentication.presentation.screens.LoginScreen
import com.carlos.chombi.feauteres.authentication.presentation.screens.RegisterScreen
import com.carlos.chombi.feauteres.busManagement.presentation.screens.BusScreen
import com.carlos.chombi.feauteres.history.presentation.screens.HistoryScreen
import com.carlos.chombi.feauteres.home.presentation.screens.HomeScreen

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppTheme {
                RegisterScreen() { }
            }
        }
    }
}