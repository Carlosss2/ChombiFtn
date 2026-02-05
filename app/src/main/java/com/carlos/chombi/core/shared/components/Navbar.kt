package com.carlos.chombi.core.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carlos.chombi.R
import com.carlos.chombi.core.ui.theme.primaryLight

@Composable
fun Navbar(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ){
        // Aquí empieza la fila de imágenes
        Row(
            modifier = Modifier.fillMaxWidth(), //
            horizontalArrangement = Arrangement.SpaceBetween, // Distribuye el espacio entre ellas
            verticalAlignment = Alignment.CenterVertically // Centra las imágenes verticalmente
        ) {
            // Imagen 1
            Image(
                painter = painterResource(id = R.drawable.home),
                contentDescription = "Descripción 1",
                modifier = Modifier.size(30.dp) // Tamaño fijo para uniformidad
            )

            // Imagen 2
            Image(
                painter = painterResource(id = R.drawable.bus__1_),
                contentDescription = "Descripción 2",
                modifier = Modifier.size(30.dp)
            )

            // Imagen 3
            Image(
                painter = painterResource(id = R.drawable.newspaper),
                contentDescription = "Descripción 3",
                modifier = Modifier.size(30.dp)
            )

            // Imagen 4
            Image(
                painter = painterResource(id = R.drawable.time_past),
                contentDescription = "Descripción 4",
                modifier = Modifier.size(30.dp)
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewNavbar(){
    Navbar()
}