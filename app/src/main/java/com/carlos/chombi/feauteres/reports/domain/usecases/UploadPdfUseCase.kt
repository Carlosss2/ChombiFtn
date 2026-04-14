package com.carlos.chombi.feauteres.reports.domain.usecases

import com.carlos.chombi.feauteres.reports.domain.repositories.ReportRepository
import java.io.File
import javax.inject.Inject

class UploadPdfUseCase @Inject constructor(
    private val repository: ReportRepository
) {
    suspend operator fun invoke(file: File): Result<Unit> {
        if (!file.exists()) return Result.failure(Exception("El archivo no existe"))
        return repository.uploadPdf(file)
    }
}