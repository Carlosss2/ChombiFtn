package com.carlos.chombi.feauteres.busManagement.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carlos.chombi.R
import com.carlos.chombi.core.ui.theme.primaryLight

@Composable
fun HeaderBus(
    onAddClick: () -> Unit = {} // <-- Añadimos este parámetro
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .background(
                primaryLight,
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
            )
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Column {
            Text(
                text = "Chombi",
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // BUSCADOR
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .shadow(10.dp, RoundedCornerShape(30.dp)),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.search),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "Buscar unidad",
                            color = Color.Black,
                            fontSize = 15.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.width(14.dp))

                // BOTÓN AGREGAR
                Card(
                    modifier = Modifier
                        .size(56.dp)
                        .shadow(10.dp, RoundedCornerShape(30.dp))
                        .clip(RoundedCornerShape(30.dp)) // Asegura que el efecto ripple (onda) respete los bordes curvos
                        .clickable { onAddClick() }, // <-- AQUÍ VA LA ACCIÓN DEL CLIC
                    shape = RoundedCornerShape(30.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.add),
                            contentDescription = null,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHeaderBus(){
    HeaderBus()
}