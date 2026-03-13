package com.carlos.chombi.feauteres.busManagement.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun CardBus(
    unidad: String,
    chofer: String,
    placa: String,
    modelo: String,
    imageUrl: String?,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEEEEEE))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen con Badge
            Box(
                modifier = Modifier
                    .width(110.dp)
                    .height(160.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Gray)
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Foto de unidad",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(Color(0xFF337060), RoundedCornerShape(8.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(text = "#$unidad", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Detalles y Botones
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Chofer: $chofer", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Placa: $placa", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Modelo: $modelo", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(
                        onClick = onEditClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF337060)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f).height(36.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("Modificar", fontSize = 12.sp)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = onDeleteClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB71C1C)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f).height(36.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("Eliminar", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}