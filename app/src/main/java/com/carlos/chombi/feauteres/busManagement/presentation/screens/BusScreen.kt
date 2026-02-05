package com.carlos.chombi.feauteres.busManagement.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carlos.chombi.R
import com.carlos.chombi.core.shared.components.Header
import com.carlos.chombi.core.shared.components.Navbar
import com.carlos.chombi.core.ui.theme.primaryLight
import com.carlos.chombi.feauteres.busManagement.presentation.components.CardBus

@Composable
fun BusScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = primaryLight, // Color de fondo general
        
        bottomBar = {
            Navbar()
        }
    ) { innerPadding ->
        // 2. El contenido principal va aquí dentro
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Importante: evita que el contenido quede tapado por la navbar
                .padding(16.dp)
        ) {
            Header()


            // Tu fila de título y botón
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Unidades registradas",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.add),
                        contentDescription = "add",
                        modifier = Modifier.size(29.dp),
                        tint = primaryLight
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            CardBus()

        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewBusScreen(){
    BusScreen()
}