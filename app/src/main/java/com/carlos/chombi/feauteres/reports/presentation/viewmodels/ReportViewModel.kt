package com.carlos.chombi.feauteres.reports.presentation.viewmodels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.carlos.chombi.core.navigation.AppNavigator
import com.carlos.chombi.feauteres.busManagement.navigation.BusRoutes
import com.carlos.chombi.feauteres.history.navigation.HistoryRoutes
import com.carlos.chombi.feauteres.home.navigation.HomeRoutes
import com.carlos.chombi.feauteres.reports.data.workers.UploadPdfWorker
import com.carlos.chombi.feauteres.reports.domain.usecases.GetPdfUrlsUseCase
import com.carlos.chombi.feauteres.reports.navigation.ReportsRoutes
import com.carlos.chombi.feauteres.reports.presentation.screens.ReportsUiState
import com.carlos.chombi.feauteres.reports.presentation.screens.UploadStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val navigator: AppNavigator,
    private val getPdfUrlsUseCase: GetPdfUrlsUseCase
): ViewModel(){

    private val _uiState = MutableStateFlow(ReportsUiState())
    val uiState: StateFlow<ReportsUiState> = _uiState

    init {
        fetchPdfs() // Cargamos la lista al iniciar
    }

    fun fetchPdfs() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingPdfs = true) }
            getPdfUrlsUseCase().fold(
                onSuccess = { urls ->
                    _uiState.update { it.copy(pdfUrls = urls, isLoadingPdfs = false) }
                },
                onFailure = {
                    _uiState.update { it.copy(isLoadingPdfs = false) }
                }
            )
        }
    }

    fun uploadReport(context: Context, uri: Uri) {
        _uiState.update { it.copy(uploadStatus = UploadStatus.UPLOADING) }

        val tempFile = File(context.cacheDir, "reporte_${System.currentTimeMillis()}.pdf")
        context.contentResolver.openInputStream(uri)?.use { it.copyTo(tempFile.outputStream()) }

        val uploadWorkRequest = OneTimeWorkRequestBuilder<UploadPdfWorker>()
            .setInputData(workDataOf("PDF_FILE_PATH" to tempFile.absolutePath))
            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
            .build()

        val workManager = WorkManager.getInstance(context)
        workManager.enqueue(uploadWorkRequest)

        viewModelScope.launch {
            workManager.getWorkInfoByIdFlow(uploadWorkRequest.id).collect { info ->
                if (info?.state == WorkInfo.State.SUCCEEDED) {
                    _uiState.update { it.copy(uploadStatus = UploadStatus.SUCCESS) }
                    fetchPdfs() // Refrescar lista automáticamente al terminar de subir
                } else if (info?.state == WorkInfo.State.FAILED) {
                    _uiState.update { it.copy(uploadStatus = UploadStatus.ERROR) }
                }
            }
        }
    }

    fun resetStatus() { _uiState.update { it.copy(uploadStatus = UploadStatus.IDLE) } }
    fun goHome() { navigator.navigate(HomeRoutes.HOME_GRAPH) }
    fun goToAddBus() { navigator.navigate(BusRoutes.BUS_GRAPH) }
    fun goToHistory() { navigator.navigate(HistoryRoutes.HISTORY_GRAPH) }
    fun goToReports(){ navigator.navigate(ReportsRoutes.REPORTS_GRAPH) }
}