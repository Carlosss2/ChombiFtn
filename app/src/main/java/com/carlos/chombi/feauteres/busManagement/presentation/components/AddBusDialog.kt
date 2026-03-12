package com.carlos.chombi.feauteres.busManagement.presentation.components

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.carlos.chombi.feauteres.busManagement.domain.entities.Bus
import java.io.File

@Composable
fun AddBusDialog(
    onDismiss: () -> Unit,
    onSave: (Bus) -> Unit,
    onTakePhotoClick: () -> Unit,
    currentPhoto: File?
) {
    var chofer by remember { mutableStateOf("") }
    var placa by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var unidad by remember { mutableStateOf("") }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) onTakePhotoClick()
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF337060))
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Registro de unidad",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                CustomOutlinedTextField(value = chofer, onValueChange = { chofer = it }, placeholder = "Nombre del Chofer")
                Spacer(modifier = Modifier.height(12.dp))
                CustomOutlinedTextField(value = placa, onValueChange = { placa = it }, placeholder = "Numero de placa")
                Spacer(modifier = Modifier.height(12.dp))
                CustomOutlinedTextField(value = modelo, onValueChange = { modelo = it }, placeholder = "Modelo de unidad")
                Spacer(modifier = Modifier.height(12.dp))
                CustomOutlinedTextField(value = unidad, onValueChange = { unidad = it }, placeholder = "Numero de unidad")

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { cameraPermissionLauncher.launch(Manifest.permission.CAMERA) },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (currentPhoto == null) Color(0xFFD9D9D9) else Color(0xFF81C784),
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = if (currentPhoto == null) "Tomar fotografía de unidad" else "¡Fotografía capturada!",
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val unitNumber = unidad.toIntOrNull()
                        if (unitNumber != null && unitNumber > 0 && chofer.isNotBlank() && placa.isNotBlank() && modelo.isNotBlank()) {
                            onSave(
                                Bus(
                                    id = "", // La API se encarga del UUID
                                    licencePlate = placa,
                                    driver = chofer,
                                    unitNumber = unitNumber,
                                    shift = "",
                                    isWorking = true,
                                    model = modelo,
                                    imageUrl = currentPhoto?.absolutePath
                                )
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A819E))
                ) {
                    Text("Registrar", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun CustomOutlinedTextField(value: String, onValueChange: (String) -> Unit, placeholder: String) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, fontWeight = FontWeight.Bold, color = Color.Black) },
        leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null, tint = Color.Black) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        singleLine = true
    )
}