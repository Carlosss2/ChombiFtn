package com.carlos.chombi.features.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.carlos.chombi.R
import com.carlos.chombi.core.ui.theme.colorRed
import com.carlos.chombi.core.ui.theme.onSurfaceVariantDark
import com.carlos.chombi.core.ui.theme.primaryLight
import com.carlos.chombi.feauteres.home.domain.entities.Bus

@Composable
fun ActiveUnitSection(
    bus: Bus,
    passengers: Int,
    maxPassengers: Int,
    timeRemaining: String,
    onAddPassenger: () -> Unit,
    onFinishLoading: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Imagen con el tag flotante (#12)
        Box(
            modifier = Modifier
                .weight(0.45f)
                .height(200.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.Gray) // Fondo gris por si la imagen falla
        ) {
            AsyncImage(
                model = bus.imageUrl ?: R.drawable.ic_launcher_background,
                contentDescription = "Unidad Activa",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // Tag flotante superior izquierdo
            Box(
                modifier = Modifier
                    .background(
                        color = onSurfaceVariantDark,
                        shape = RoundedCornerShape(bottomEnd = 16.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "#${bus.unitNumber}",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }

        // Tarjeta de información derecha
        Card(
            modifier = Modifier
                .weight(0.55f)
                .height(200.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = onSurfaceVariantDark),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Pasajeros: $passengers/$maxPassengers",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color.Black
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Tiempo: $timeRemaining ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = Color.Black
                    )
                    Icon(
                        imageVector = Icons.Default.Restore,
                        contentDescription = "Tiempo",
                        modifier = Modifier.size(18.dp),
                        tint = Color.Black
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = onAddPassenger,
                        colors = ButtonDefaults.buttonColors(containerColor = primaryLight),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                        modifier = Modifier.weight(1f).padding(end = 4.dp),
                        enabled = passengers < maxPassengers // Deshabilita el botón si llega a 14
                    ) {
                        Text(text = "Agregar", fontSize = 11.sp, maxLines = 1)
                    }

                    Button(
                        onClick = onFinishLoading,
                        colors = ButtonDefaults.buttonColors(containerColor = colorRed),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                        modifier = Modifier.weight(1f).padding(start = 4.dp)
                    ) {
                        Text(text = "Terminar", fontSize = 11.sp, maxLines = 1)
                    }
                }
            }
        }
    }
}