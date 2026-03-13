package com.carlos.chombi.features.home.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carlos.chombi.core.ui.theme.onPrimaryLight
import com.carlos.chombi.core.ui.theme.primaryLight
import com.carlos.chombi.core.ui.theme.scrimLight
import com.carlos.chombi.features.home.presentation.components.ActiveUnitSection
import com.carlos.chombi.features.home.presentation.components.NextUnitCard
import com.carlos.chombi.feauteres.home.domain.entities.Bus
import com.carlos.chombi.feauteres.home.presentation.screens.HomeUiState

@Composable
fun DispatchScreen(
    uiState: HomeUiState,
    onAddPassenger: () -> Unit,
    onFinishLoading: () -> Unit,
    onSkipUnit: (Bus) -> Unit,
    onEndDay: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(onPrimaryLight)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // HEADER: Título y botón "Finalizar día"
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Unidad en carga",
                color = scrimLight,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp
            )
            Button(
                onClick = onEndDay,
                colors = ButtonDefaults.buttonColors(containerColor = primaryLight),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Finalizar día", fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // SECCIÓN 1: Unidad Activa
        uiState.activeBus?.let { activeBus ->
            ActiveUnitSection(
                bus = activeBus,
                passengers = uiState.currentPassengers,
                maxPassengers = uiState.maxPassengers,
                timeRemaining = uiState.timeRemaining,
                onAddPassenger = onAddPassenger,
                onFinishLoading = onFinishLoading
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // SECCIÓN 2: Título de siguientes unidades
        Text(
            text = "Siguiente unidad",
            color = scrimLight,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 22.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // LISTA DE SIGUIENTES UNIDADES
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(uiState.nextBuses) { bus ->
                NextUnitCard(
                    bus = bus,
                    onSkip = { onSkipUnit(bus) }
                )
            }
        }
    }
}