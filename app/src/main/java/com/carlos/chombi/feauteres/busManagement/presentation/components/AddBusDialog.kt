package com.carlos.chombi.feauteres.busManagement.presentation.components



import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddBusDialog(onDismiss: () -> Unit) {

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Agregar unidad") },
        text = {
            Column {
                OutlinedTextField(value = "", onValueChange = {}, label = { Text("Unidad") })
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = "", onValueChange = {}, label = { Text("Chofer") })
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = "", onValueChange = {}, label = { Text("Placa") })
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
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
