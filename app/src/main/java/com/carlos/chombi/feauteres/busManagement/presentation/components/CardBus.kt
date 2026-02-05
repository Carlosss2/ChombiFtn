package com.carlos.chombi.feauteres.busManagement.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carlos.chombi.core.ui.theme.errorLight
import com.carlos.chombi.core.ui.theme.onPrimaryLight
import com.carlos.chombi.core.ui.theme.primaryLight

@Composable
fun CardBus(
    unidad: String = "#27",
    chofer: String = "Carlos Gael",
    placa: String = "B13 CX1 318",
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), // Margen externo
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEEEEEE)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 16.dp) // Relleno interno
        ) {
            // --- FILA SUPERIOR: Unidad + Icono Editar ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Unidad: $unidad",
                    color = Color(0xFF00695C), // Color Verde/Teal oscuro
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                IconButton(
                    onClick = onEditClick,
                    modifier = Modifier.size(36.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = primaryLight,
                        contentColor = Color.White //
                    )
                ) {
                    Icon(

                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Editar"

                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // --- FILA MEDIA: Solo texto Chofer ---
            // Usamos Row o Text directo, aquí Text es suficiente pero con Span queda mejor si quieres "Chofer:" en negrita y el nombre normal
            Text(
                text = "Chofer: $chofer",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // --- FILA INFERIOR: Placa + Icono Borrar ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom // Alineado abajo por si el texto crece
            ) {
                Text(
                    text = "Placa: $placa",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 4.dp) // Pequeño ajuste visual
                )


                IconButton(
                    onClick = onDeleteClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = errorLight,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .size(36.dp) // Tamaño del circulo

                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Eliminar",
                        modifier = Modifier.size(20.dp),
                        tint = Color.White
                    )
                }

            }
        }
    }
}

@Preview
@Composable
fun PreviewCardBus() {

            CardBus()

}