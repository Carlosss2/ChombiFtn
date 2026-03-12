package com.carlos.chombi.feauteres.authentication.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.carlos.chombi.R
import com.carlos.chombi.core.ui.theme.primaryLight
import com.carlos.chombi.feauteres.authentication.presentation.components.RegisterResultDialog
import com.carlos.chombi.feauteres.authentication.presentation.viewmodels.RegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel= hiltViewModel(), onLoginClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    // Estados locales para el Dropdown
    var expanded by remember { mutableStateOf(false) }
    val roles = listOf("Checador", "Conductor")
    var selectedRol by remember { mutableStateOf("Selecciona rol") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryLight)
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(54.dp))
        Text(
            text = "Chombi",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Comienza a gestionar tu ruta",
            fontSize = 20.sp,
            color = Color.White
        )
        Image(
            painter = painterResource(R.drawable.bus),
            contentDescription = "bus_icon",
            modifier = Modifier
                .size(80.dp)
                .offset(x = 260.dp, y = 15.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White, RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .padding(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { viewModel.goToLogin() }
            ) {
                Icon(
                    painter = painterResource(R.drawable.angle_circle_left),
                    contentDescription = "arrow",
                    modifier = Modifier.size(24.dp),
                    tint = primaryLight
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Inicio de sesion",
                    fontSize = 14.sp,
                    color = primaryLight
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Registro",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = primaryLight
            )



            Spacer(modifier = Modifier.height(20.dp))

            // --- SELECTOR DE ROL (DROPDOWN) ---
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = selectedRol,
                    onValueChange = {},
                    readOnly = true, // Evita que el usuario escriba
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.LightGray,
                        focusedBorderColor = primaryLight
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .menuAnchor() // Vincula el menú al TextField
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    roles.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(text = opcion) },
                            onClick = {
                                selectedRol = opcion
                                expanded = false
                                viewModel.onRoleChange(opcion)
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(36.dp))

            // Campo de nombres
            CustomTextField(
                value = uiState.name,
                onValueChange = { viewModel.onNameChange(it) },
                placeholder = "Nombre",
                iconRes = R.drawable.user
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de apellido
            CustomTextField(
                value = uiState.lastName,
                onValueChange = { viewModel.onLastNameChange(it) },
                placeholder = "Apellido",
                iconRes = R.drawable.user
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de correo
            CustomTextField(
                value = uiState.email,
                onValueChange = { viewModel.onEmailChange(it) },
                placeholder = "Correo electronico",
                iconRes = R.drawable.gmain_icon
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de contraseña
            CustomTextField(
                value = uiState.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                placeholder = "Contraseña",
                iconRes = R.drawable.lock,
                isPassword = true
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Botón con estado de carga
            Button(
                onClick = { viewModel.onRegister() },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                enabled = !uiState.isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = primaryLight),
                shape = RoundedCornerShape(12.dp)
            ) {
                if (uiState.isLoading) {
                    Text("Procesando...")
                } else {
                    Text("Registrar", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        //dialog
        if (uiState.isSuccess) {
            RegisterResultDialog(
                isSuccess = true,
                message = "Tu cuenta fue creada correctamente",
                onDismiss = { viewModel.clearResult() }
            )
        }


        uiState.error?.let {
            RegisterResultDialog(
                isSuccess = false,
                message = "No se pudo completar el registro. Intenta nuevamente.",
                onDismiss = { viewModel.clearResult() }
            )
        }
    }
}


@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    iconRes: Int,
    isPassword: Boolean = false
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(placeholder) },
        leadingIcon = {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        },
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.LightGray,
            focusedBorderColor = primaryLight
        )
    )
}