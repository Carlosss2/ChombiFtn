package com.carlos.chombi.feauteres.authentication.presentation.screens

import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.carlos.chombi.R
import com.carlos.chombi.core.ui.theme.primaryLight
import com.carlos.chombi.core.ui.theme.secondaryLight
import com.carlos.chombi.feauteres.authentication.presentation.components.LoginResultDialog
import com.carlos.chombi.feauteres.authentication.presentation.viewmodels.LoginViewModel

// FUNCIÓN CLAVE PARA EXTRAER EL FRAGMENT ACTIVITY
fun Context.findActivity(): FragmentActivity? {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is FragmentActivity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(), onRegisterClick: () -> Unit, onBusClick: ()-> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val fragmentActivity = remember(context) { context.findActivity() } // Obtenemos la actividad correcta

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

            Spacer(modifier = Modifier.height(36.dp))
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

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Biométrico
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        // Lanzamos biometría solo si encontramos el FragmentActivity
                        fragmentActivity?.let { activity ->
                            viewModel.loginWithBiometrics(activity)
                        }
                    },
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Fingerprint,
                        contentDescription = "Login con huella",
                        modifier = Modifier.size(48.dp),
                        tint = primaryLight
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))


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
                    color = Color.Gray,
                    modifier = Modifier.clickable { viewModel.goToRegister() }
                )
            }

        }
        if (uiState.isLoggedIn) {
            LoginResultDialog(
                isSuccess = true,
                message = "Bienvenido a Chombi",
                onDismiss = {
                    viewModel.clearResult()
                    onBusClick()
                }
            )
        }

        uiState.error?.let { errorMessage ->
            LoginResultDialog(
                isSuccess = false,
                message = errorMessage,
                onDismiss = { viewModel.clearResult() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLogin(){
    LoginScreen(
        onRegisterClick = {},
        onBusClick = {}
    )
}