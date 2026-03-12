package com.carlos.chombi.core.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carlos.chombi.R
import com.carlos.chombi.core.ui.theme.onPrimaryLight
import com.carlos.chombi.core.ui.theme.primaryLight

@Composable
fun Navbar(
    onHomeClick: () -> Unit,
    onAddClick: () -> Unit,
    onHistoryClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Contenedor principal
        Row(
            modifier = Modifier
                .weight(1f)
                .height(70.dp) // ALTURA MAYOR
                .clip(RoundedCornerShape(40.dp))
                .background(primaryLight)
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(id = R.drawable.homen),
                contentDescription = "Home",
                modifier = Modifier
                    .size(30.dp)
                    .clickable { onHomeClick() },
                tint = onPrimaryLight
            )

            Icon(
                painter = painterResource(id = R.drawable.bus__1_),
                contentDescription = "Bus",
                modifier = Modifier.size(30.dp)
                    .clickable { onAddClick() },
                tint = onPrimaryLight
            )

            Icon(
                painter = painterResource(id = R.drawable.time_past),
                contentDescription = "Time",
                modifier = Modifier.size(30.dp)
                    .clickable { onHistoryClick() },
                tint = onPrimaryLight
            )

            Icon(
                painter = painterResource(id = R.drawable.newspaper),
                contentDescription = "News",
                modifier = Modifier.size(30.dp),
                tint = onPrimaryLight
            )
        }
        
        Box(
            modifier = Modifier
                .size(70.dp) // MISMA ALTURA QUE EL NAVBAR
                .clip(RoundedCornerShape(40.dp))
                .background(primaryLight),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.add),
                contentDescription = "Add",
                modifier = Modifier.size(30.dp),
                tint = onPrimaryLight
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNavbar(){
   // Navbar()
}