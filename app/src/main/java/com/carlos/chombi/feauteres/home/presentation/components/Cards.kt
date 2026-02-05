package com.carlos.chombi.feauteres.home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carlos.chombi.R

//  TARJETA GRANDE (Unidad Activa)
@Composable
fun ActiveBusCard(
    unidad: String = "#27",
    tiempoRestante: String = "12:01",
    pasajeros: String = "12/14",
    placa: String = "B13 CX1 318",
    chofer: String = "Carlos Gael"
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEEEEEE)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Fila Superior: Unidad y Tiempo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Unidad: $unidad",
                    color = Color(0xFF00695C), // Verde azulado
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "Tiempo restante: $tiempoRestante",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Fila Central: Datos a la izquierda, Imagen a la derecha
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Columna de Datos
                Column(modifier = Modifier.weight(1f)) {
                    // Fila de Pasajeros con botón +
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Pasajeros: $pasajeros",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        IconButton(onClick = { /* Lógica agregar pasajero */ }) {
                            Icon(
                                imageVector = Icons.Outlined.AddCircle,
                                contentDescription = "Agregar pasajero",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(text = "Placa: $placa", fontWeight = FontWeight.Bold, fontSize = 14.sp)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Chofer: $chofer", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }

                // Imagen del Bus a la derecha
                Image(

                    painter = painterResource(id = R.drawable.chombi),
                    contentDescription = "Bus illustration",
                    modifier = Modifier
                        .size(100.dp) // Ajusta el tamaño
                        .padding(start = 8.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

// 2. TARJETA "SIGUIENTE UNIDAD"
@Composable
fun NextBusCard(
    unidad: String = "#28",
    pasajeros: String = "0/14",
    placa: String = "A22 YT5 999",
    chofer: String = "Juan Pérez"
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEEEEEE)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Solo Unidad (sin tiempo)
            Text(
                text = "Unidad: $unidad",
                color = Color(0xFF00695C),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Datos (sin imagen al lado)
            Text(text = "Pasajeros: $pasajeros", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Placa: $placa", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Chofer: $chofer", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}

// CONTENEDOR PRINCIPAL (Para mostrar cómo usarlas)
@Composable
fun Cards() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Tarjeta Grande
        ActiveBusCard()

        Spacer(modifier = Modifier.height(24.dp))

        // Título de la sección
        Text(
            text = "Siguiente unidad",
            color = Color.White, // Asumo fondo oscuro por tus capturas previas
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Tarjeta Pequeña
        NextBusCard()

        Spacer(modifier = Modifier.height(12.dp))

        // Otra tarjeta pequeña de ejemplo (vacía visualmente como en tu imagen)
        NextBusCard(unidad = "---", pasajeros = "--/--", placa = "---", chofer = "---")
    }
}

@Preview(showBackground = true) // Fondo oscuro para ver contraste
@Composable
fun PreviewCards() {
    Cards()
}