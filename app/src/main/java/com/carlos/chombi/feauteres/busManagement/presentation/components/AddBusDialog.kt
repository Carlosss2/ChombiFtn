package com.carlos.chombi.feauteres.busManagement.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.carlos.chombi.feauteres.busManagement.domain.entities.Bus

@Composable
fun AddBusDialog(
    onDismiss: () -> Unit,
    onSave: (Bus) -> Unit
) {
    var unidad by remember { mutableStateOf("") }
    var chofer by remember { mutableStateOf("") }
    var placa by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Agregar unidad") },
        text = {
            Column {
                OutlinedTextField(value = unidad, onValueChange = { unidad = it }, label = { Text("Unidad") })
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = chofer, onValueChange = { chofer = it }, label = { Text("Chofer") })
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = placa, onValueChange = { placa = it }, label = { Text("Placa") })
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val unitNumber = unidad.toIntOrNull()

                    if (unitNumber != null && unitNumber > 0 && chofer.isNotBlank() && placa.isNotBlank()) {
                        onSave(
                            Bus(
                                id = 0,
                                licencePlate = placa,
                                driver = chofer,
                                unitNumber = unitNumber,
                                shift = "",
                                isWorking = true
                            )
                        )
                    }
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}