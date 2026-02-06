package com.carlos.chombi.feauteres.busManagement.presentation.components


import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun EditBusDialog(onDismiss: () -> Unit) {

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar unidad") },
        text = {
            OutlinedTextField(
                value = "Carlos Gael",
                onValueChange = {},
                label = { Text("Chofer") }
            )
        },
        confirmButton = {
            Button(onClick = onDismiss) {
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
