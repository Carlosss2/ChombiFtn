package com.carlos.chombi.feauteres.reports.presentation.screens

enum class UploadStatus {
    IDLE, UPLOADING, SUCCESS, ERROR
}

data class ReportsUiState(
    val uploadStatus: UploadStatus = UploadStatus.IDLE,
    val pdfUrls: List<String> = emptyList(),
    val isLoadingPdfs: Boolean = false
)