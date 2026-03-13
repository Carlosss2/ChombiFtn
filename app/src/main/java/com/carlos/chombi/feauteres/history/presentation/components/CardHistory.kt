package com.carlos.chombi.feauteres.history.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Importando tus colores del tema
import com.carlos.chombi.core.ui.theme.primaryLight
import com.carlos.chombi.core.ui.theme.surfaceContainerLowestLight
import com.carlos.chombi.core.ui.theme.onSurfaceLight
import com.carlos.chombi.core.ui.theme.outlineVariantLight
import com.carlos.chombi.core.ui.theme.onSurfaceVariantLight
import com.carlos.chombi.core.ui.theme.surfaceVariantLight
import com.carlos.chombi.feauteres.history.domain.entities.BusHistory

@Composable
fun CardHistory(
    bus: BusHistory,
    modifier: Modifier = Modifier
) {
    // Estado para controlar la expansión local de cada tarjeta
    var expanded by remember { mutableStateOf(false) }

    // Animación de la flecha (0 a 180 grados)
    val rotationState by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "Arrow Rotation"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { expanded = !expanded }
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            ),
        colors = CardDefaults.cardColors(
            containerColor = surfaceContainerLowestLight
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // --- ENCABEZADO (Siempre visible) ---
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = null,
                    tint = primaryLight,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = bus.date,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = onSurfaceLight,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = if (expanded) "Ocultar" else "Ver detalles",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = primaryLight
                )

                Spacer(modifier = Modifier.width(4.dp))

                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = primaryLight,
                    modifier = Modifier.rotate(rotationState)
                )
            }

            // --- CONTENIDO DESPLEGABLE (Detalles del viaje) ---
            if (expanded) {
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = outlineVariantLight, thickness = 1.dp)
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = surfaceVariantLight, shape = RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DirectionsCar,
                        contentDescription = null,
                        tint = onSurfaceVariantLight,
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Placa: ${bus.licensePlate}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = onSurfaceLight
                        )
                        Text(
                            text = "Turno: ${bus.shift}",
                            fontSize = 12.sp,
                            color = onSurfaceVariantLight
                        )
                    }

                    Text(
                        text = bus.driverName,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryLight
                    )
                }
            }
        }
    }
}