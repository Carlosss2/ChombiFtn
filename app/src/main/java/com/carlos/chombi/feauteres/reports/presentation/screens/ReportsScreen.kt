package com.carlos.chombi.feauteres.reports.presentation.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.carlos.chombi.core.shared.components.Navbar
import com.carlos.chombi.core.ui.theme.onPrimaryLight
import com.carlos.chombi.core.ui.theme.primaryLight
import com.carlos.chombi.feauteres.reports.presentation.components.HeaderReport
import com.carlos.chombi.feauteres.reports.presentation.viewmodels.ReportViewModel

@Composable
fun ReportsScreen(viewModel: ReportViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val pdfPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            viewModel.uploadReport(context, it)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = onPrimaryLight,
        bottomBar = {
            Navbar(
                onHomeClick = { viewModel.goHome() },
                onAddClick = { viewModel.goToAddBus() },
                onHistoryClick = { viewModel.goToHistory() },
                onReportsClick = { viewModel.goToReports() }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HeaderReport()
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Reportes Operativos",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Text(
                text = "Sube los reportes en formato PDF. El sistema los procesará automáticamente.",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            //
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(primaryLight.copy(alpha = 0.1f))
                    .border(2.dp, primaryLight.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                    .clickable(enabled = uiState.uploadStatus != UploadStatus.UPLOADING) {
                        pdfPickerLauncher.launch("application/pdf")
                    },
                contentAlignment = Alignment.Center
            ) {
                if (uiState.uploadStatus == UploadStatus.UPLOADING) {
                    // Muestra animación de carga mientras se ejecuta el WorkManager
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = primaryLight)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Subiendo archivo...", color = primaryLight, fontWeight = FontWeight.Medium)
                    }
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.UploadFile, contentDescription = null, modifier = Modifier.size(64.dp), tint = primaryLight)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Toca para seleccionar un archivo", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = primaryLight)
                        Text("Solo formato PDF (Máx. 10MB)", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }
    }

    // dialogs
    if (uiState.uploadStatus == UploadStatus.SUCCESS) {
        StatusDialog(
            title = "¡Éxito!",
            message = "El reporte PDF se ha subido correctamente al servidor.",
            isSuccess = true,
            onDismiss = { viewModel.resetStatus() }
        )
    } else if (uiState.uploadStatus == UploadStatus.ERROR) {
        StatusDialog(
            title = "Error de Subida",
            message = "Hubo un problema al subir el archivo. Revisa tu conexión a internet o intenta de nuevo.",
            isSuccess = false,
            onDismiss = { viewModel.resetStatus() }
        )
    }
}


@Composable
fun StatusDialog(
    title: String,
    message: String,
    isSuccess: Boolean,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = if (isSuccess) Icons.Default.CheckCircle else Icons.Default.Error,
                    contentDescription = null,
                    tint = if (isSuccess) Color(0xFF4CAF50) else Color(0xFFF44336), // Verde o Rojo
                    modifier = Modifier.size(60.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = message,
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = primaryLight),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Aceptar", color = Color.White)
                }
            }
        }
    }
}