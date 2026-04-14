package com.carlos.chombi.feauteres.reports.data.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.carlos.chombi.feauteres.reports.domain.usecases.UploadPdfUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.io.File

@HiltWorker
class UploadPdfWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val uploadPdfUseCase: UploadPdfUseCase
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        // 1. Recibimos la ruta del archivo que mandó el ViewModel
        val filePath = inputData.getString("PDF_FILE_PATH") ?: return Result.failure()
        val file = File(filePath)

        // 2. Ejecutamos el caso de uso (llamada a la API)
        val result = uploadPdfUseCase(file)

        // 3. Manejamos la respuesta
        return result.fold(
            onSuccess = { Result.success() }, // Termina el servicio exitosamente
            onFailure = { Result.retry() }    // Si falla (ej. sin internet), Android lo reintenta luego
        )
    }
}