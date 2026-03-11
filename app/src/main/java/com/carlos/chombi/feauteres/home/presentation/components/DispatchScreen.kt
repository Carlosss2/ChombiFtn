package com.carlos.chombi.features.home.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carlos.chombi.core.ui.theme.onPrimaryLight
import com.carlos.chombi.core.ui.theme.primaryLight
import com.carlos.chombi.core.ui.theme.scrimLight
import com.carlos.chombi.features.home.presentation.components.ActiveUnitSection
import com.carlos.chombi.features.home.presentation.components.NextUnitCard

@Composable
fun DispatchScreen() {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(onPrimaryLight)
            .padding(16.dp)
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
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = primaryLight),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Finalizar día", fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // SECCIÓN 1: Unidad Activa
        ActiveUnitSection()

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
        NextUnitCard(unitNumber = "#41", driverName = "Carlos Castro")

        Spacer(modifier = Modifier.height(16.dp))

        NextUnitCard(unitNumber = "#11", driverName = "Sergio Perez")
    }
}

@Preview(showBackground = true)
@Composable
fun DispatchScreenPreview() {
    DispatchScreen()
}