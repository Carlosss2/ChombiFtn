package com.carlos.chombi.feauteres.reports.presentation.screens

enum class UploadStatus {
    IDLE,       // Esperando
    UPLOADING,  // Subiendo (Mostrar progress de carga)
    SUCCESS,    // Éxito (Mostrar alerta verde)
    ERROR       // Error (Mostrar alerta roja)
}

data class ReportsUiState(
    val uploadStatus: UploadStatus = UploadStatus.IDLE
)