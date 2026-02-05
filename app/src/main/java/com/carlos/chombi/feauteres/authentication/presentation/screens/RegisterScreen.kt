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
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carlos.chombi.R
import com.carlos.chombi.core.ui.theme.primaryLight

@Composable
fun RegisterScreen(){
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
                .offset(x = 260.dp, y = 15.dp) // Lo mueve 50dp a la derecha y 20dp hacia abajo
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White, RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .padding(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { /* Navegar a login */ }
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
            Spacer(modifier = Modifier.height(34.dp))

            // Campo de nombres
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Nombre") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.user),
                        contentDescription = "Name",
                        modifier = Modifier
                            .size(24.dp)
                            .offset(x = 2.dp, y = 1.dp)

                    )
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.LightGray,
                    focusedBorderColor = primaryLight
                )
            )

            Spacer(modifier = Modifier.height(34.dp))

            // Campo
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Apellido") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.user),
                        contentDescription = "FirstName",
                        modifier = Modifier
                            .size(24.dp)
                            .offset(x = 2.dp, y = 1.dp)

                    )
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.LightGray,
                    focusedBorderColor = primaryLight
                )
            )

            Spacer(modifier = Modifier.height(36.dp))

            // Campo de correo electrónico
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Correo electronico") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.gmain_icon),
                        contentDescription = "Email"
                    )
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.LightGray,
                    focusedBorderColor = primaryLight
                )
            )

            Spacer(modifier = Modifier.height(36.dp))

            // Campo de contraseña
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Contraseña") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.lock),
                        contentDescription = "Password",
                        modifier = Modifier
                            .size(24.dp)
                            .offset(x = 2.dp, y = 1.dp)
                    )
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.LightGray,
                    focusedBorderColor = primaryLight
                )
            )

            Spacer(modifier = Modifier.height(36.dp))

            // Botón
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryLight
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Registrar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
        }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegister(){
    RegisterScreen()
}