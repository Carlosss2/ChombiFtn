package com.carlos.chombi.feauteres.reports.presentation.viewmodels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.carlos.chombi.core.navigation.AppNavigator
import com.carlos.chombi.feauteres.busManagement.navigation.BusRoutes
import com.carlos.chombi.feauteres.history.navigation.HistoryRoutes
import com.carlos.chombi.feauteres.home.navigation.HomeRoutes
import com.carlos.chombi.feauteres.reports.data.workers.UploadPdfWorker
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
): ViewModel(){

    private val _uiState = MutableStateFlow(ReportsUiState())
    val uiState: StateFlow<ReportsUiState> = _uiState

    fun uploadReport(context: Context, uri: Uri) {
        // 1. Cambiamos estado a "Subiendo"
        _uiState.update { it.copy(uploadStatus = UploadStatus.UPLOADING) }

        // 2. Copiamos el archivo
        val tempFile = File(context.cacheDir, "reporte_chombi_${System.currentTimeMillis()}.pdf")
        context.contentResolver.openInputStream(uri)?.use { input ->
            tempFile.outputStream().use { output -> input.copyTo(output) }
        }

        val inputData = workDataOf("PDF_FILE_PATH" to tempFile.absolutePath)
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        val uploadWorkRequest = OneTimeWorkRequestBuilder<UploadPdfWorker>()
            .setConstraints(constraints)
            .setInputData(inputData)
            .build()

        val workManager = WorkManager.getInstance(context)
        workManager.enqueue(uploadWorkRequest)

        // 3.Observamos el estado del WorkManager
        viewModelScope.launch {
            workManager.getWorkInfoByIdFlow(uploadWorkRequest.id).collect { workInfo ->
                if (workInfo != null) {
                    when (workInfo.state) {
                        WorkInfo.State.SUCCEEDED -> {
                            _uiState.update { it.copy(uploadStatus = UploadStatus.SUCCESS) }
                        }
                        WorkInfo.State.FAILED -> {
                            _uiState.update { it.copy(uploadStatus = UploadStatus.ERROR) }
                        }
                        else -> { /* Sigue ejecutándose o encolado */ }
                    }
                }
            }
        }
    }


    fun resetStatus() {
        _uiState.update { it.copy(uploadStatus = UploadStatus.IDLE) }
    }


    fun goHome() { navigator.navigate(HomeRoutes.HOME_GRAPH) }
    fun goToAddBus() { navigator.navigate(BusRoutes.BUS_GRAPH) }
    fun goToHistory() { navigator.navigate(HistoryRoutes.HISTORY_GRAPH) }
    fun goToReports(){ navigator.navigate(ReportsRoutes.REPORTS_GRAPH) }
}