package com.carlos.chombi.feauteres.reports.domain.repositories
import java.io.File

interface ReportRepository {
    suspend fun uploadPdf(file: File): Result<Unit>
}