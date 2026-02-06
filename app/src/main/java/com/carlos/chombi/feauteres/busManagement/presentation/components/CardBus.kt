package com.carlos.chombi.feauteres.busManagement.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carlos.chombi.core.ui.theme.errorLight
import com.carlos.chombi.core.ui.theme.primaryLight

@Composable
fun CardBus(
    unidad: String = "#27",
    chofer: String = "Carlos Gael",
    placa: String = "B13 CX1 318",
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEEEEEE))
    ) {

        Column(modifier = Modifier.padding(20.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Unidad: $unidad",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00695C)
                )

                IconButton(
                    onClick = onEditClick,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = primaryLight,
                        contentColor = Color.White
                    )
                ) {
                    Icon(Icons.Outlined.Edit, contentDescription = "Editar")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Chofer: $chofer",
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Placa: $placa",
                    fontWeight = FontWeight.Bold
                )

                IconButton(
                    onClick = onDeleteClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = errorLight,
                        contentColor = Color.White
                    )
                ) {
                    Icon(Icons.Filled.Delete, contentDescription = "Eliminar")
                }
            }
        }
    }
}
