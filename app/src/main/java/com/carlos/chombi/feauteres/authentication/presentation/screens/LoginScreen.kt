package com.carlos.chombi.feauteres.authentication.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.carlos.chombi.R
import com.carlos.chombi.core.ui.theme.primaryLight
import com.carlos.chombi.core.ui.theme.secondaryLight
import com.carlos.chombi.feauteres.authentication.presentation.components.LoginResultDialog
import com.carlos.chombi.feauteres.authentication.presentation.viewmodels.LoginViewModel
import com.carlos.chombi.feauteres.authentication.presentation.viewmodels.LoginViewModelFactory

@Composable
fun LoginScreen(
    factory: LoginViewModelFactory
) {
    val viewModel: LoginViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryLight)
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(74.dp))

        Text(
            text = "Hola..!",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Bienvenido a Chombi",
            fontSize = 20.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(18.dp))

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
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                )
                .padding(24.dp)
        ) {

            Text(
                text = "Inicio de sesion",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = primaryLight
            )

            Spacer(modifier = Modifier.height(34.dp))

            // Email
            TextField(
                value = uiState.email,
                onValueChange = viewModel::onEmailChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Correo electronico") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.gmain_icon),
                        contentDescription = null
                    )
                },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.LightGray,
                    focusedBorderColor = primaryLight
                )
            )

            Spacer(modifier = Modifier.height(36.dp))

            // Password
            TextField(
                value = uiState.password,
                onValueChange = viewModel::onPasswordChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Contraseña") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.lock),
                        contentDescription = "Password" ,
                        modifier = Modifier
                            .size(24.dp)
                            .offset(x = 2.dp, y = 1.dp)
                    )
                },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.LightGray,
                    focusedBorderColor = primaryLight
                )
            )

            Spacer(modifier = Modifier.height(36.dp))

            // Botón login
            Button(
                onClick = viewModel::login,
                enabled = !uiState.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryLight),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (uiState.isLoading) "Cargando..." else "Inicio de sesión",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Texto registro (solo UI, sin navegación aquí)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("¿No tienes cuenta? ")
                        withStyle(
                            style = SpanStyle(
                                color = secondaryLight,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("Registrarme")
                        }
                    },
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

        }
        if (uiState.isLoggedIn) {
            LoginResultDialog(
                isSuccess = true,
                message = "Bienvenido a Chombi",
                onDismiss = { viewModel.clearResult() }
            )
        }


        uiState.error?.let {
            LoginResultDialog(
                isSuccess = false,
                message = "Correo o contraseña incorrectos",
                onDismiss = { viewModel.clearResult() }
            )
        }
    }
}