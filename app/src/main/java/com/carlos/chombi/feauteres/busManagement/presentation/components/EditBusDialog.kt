package com.carlos.chombi.feauteres.busManagement.presentation.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.carlos.chombi.feauteres.busManagement.domain.entities.Bus

@Composable
fun EditBusDialog(
    bus: Bus,
    onDismiss: () -> Unit,
    onSave: (Bus) -> Unit
) {
    var chofer by remember { mutableStateOf(bus.driver) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar unidad") },
        text = {
            OutlinedTextField(
                value = chofer,
                onValueChange = { chofer = it },
                label = { Text("Chofer") }
            )
        },
        confirmButton = {
            Button(onClick = { onSave(bus.copy(driver = chofer)) }) {
                Text("Actualizar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
