package com.carlos.chombi.feauteres.authentication.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carlos.chombi.core.ui.theme.errorLight
import com.carlos.chombi.core.ui.theme.primaryLight

@Composable
fun LoginResultDialog(
    isSuccess: Boolean,
    message: String,
    onDismiss: () -> Unit
) {
    val dialogColor = if (isSuccess) primaryLight else errorLight
    val title = if (isSuccess) "Inicio de sesión exitoso" else "Error al iniciar sesión"

    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(20.dp),
        containerColor = Color.White,
        title = {
            Text(
                text = title,
                fontSize = 20.sp,
                color = dialogColor
            )
        },
        text = {
            Text(
                text = message,
                fontSize = 16.sp,
                color = Color.DarkGray
            )
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = dialogColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Aceptar", color = Color.White)
            }
        }
    )
}
