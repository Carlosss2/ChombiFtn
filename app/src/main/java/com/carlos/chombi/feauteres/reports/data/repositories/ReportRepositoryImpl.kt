package com.carlos.chombi.feauteres.reports.data.repositories

import android.util.Log
import com.carlos.chombi.core.network.ChombiApi
import com.carlos.chombi.feauteres.reports.domain.repositories.ReportRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val api: ChombiApi
) : ReportRepository {
    override suspend fun uploadPdf(file: File): Result<Unit> {
        return try {
            val requestFile = file.asRequestBody("application/pdf".toMediaTypeOrNull())
            val pdfPart = MultipartBody.Part.createFormData("pdf", file.name, requestFile)
            api.uploadPdf(pdfPart)
            Result.success(Unit)
        } catch (e: Exception) {

            Log.e("CHOMBI_ERROR", "Error al subir PDF al servidor: ${e.message}", e)
            Result.failure(e)
        }

    }
    override suspend fun getPdfUrls(): Result<List<String>> {
        return try {
            val response = api.getPdfUrls()
            Result.success(response.pdfUrls)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
