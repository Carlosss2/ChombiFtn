package com.carlos.chombi.feauteres.home.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.carlos.chombi.core.shared.components.Navbar
import com.carlos.chombi.core.ui.theme.onPrimaryLight
import com.carlos.chombi.core.ui.theme.primaryLight
import com.carlos.chombi.features.home.presentation.screens.DispatchScreen
import com.carlos.chombi.feauteres.home.presentation.components.HeaderHome
import com.carlos.chombi.feauteres.home.presentation.viewmodels.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = onPrimaryLight,
        bottomBar = {
            Navbar(
                onHomeClick = { viewModel.goHome() },
                onAddClick = { viewModel.goToAddBus() },
                onHistoryClick = { viewModel.goToHistory() }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HeaderHome()
            Spacer(modifier = Modifier.height(20.dp))

            if (uiState.isDayStarted) {
                // Mostrar panel de despacho pasándole el estado y los eventos
                DispatchScreen(
                    uiState = uiState,
                    onAddPassenger = viewModel::addPassenger,
                    onFinishLoading = viewModel::finishLoading,
                    onSkipUnit = viewModel::skipUnit,
                    onEndDay = viewModel::endDay
                )
            } else {
                // Pantalla de inicio de jornada
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = viewModel::startDay,
                        colors = ButtonDefaults.buttonColors(containerColor = primaryLight),
                        modifier = Modifier.height(60.dp),
                        shape = MaterialTheme.shapes.extraLarge
                    ) {
                        Text(text = "Comenzar Día", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}