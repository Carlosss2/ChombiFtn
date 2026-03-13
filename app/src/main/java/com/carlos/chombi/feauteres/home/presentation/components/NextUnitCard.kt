package com.carlos.chombi.features.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carlos.chombi.core.ui.theme.colorRed
import com.carlos.chombi.core.ui.theme.onSurfaceVariantDark
import com.carlos.chombi.core.ui.theme.primaryLight
import com.carlos.chombi.feauteres.home.domain.entities.Bus

@Composable
fun NextUnitCard(
    bus: Bus,
    onSkip: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = onSurfaceVariantDark),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Círculo con el número de unidad
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(primaryLight)
            ) {
                Text(
                    text = "#${bus.unitNumber}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            // Nombre del Chofer
            Text(
                text = "Chofer: ${bus.driver}",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color.Black,
                modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
                maxLines = 1
            )

            // Botón de Saltar
            Button(
                onClick = onSkip,
                colors = ButtonDefaults.buttonColors(containerColor = colorRed),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 0.dp)
            ) {
                Text(text = "Saltar", fontSize = 14.sp)
            }
        }
    }
}