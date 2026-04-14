package com.carlos.chombi.feauteres.reports.domain.usecases

import com.carlos.chombi.feauteres.reports.domain.repositories.ReportRepository
import javax.inject.Inject

class GetPdfUrlsUseCase @Inject constructor(
    private val repository: ReportRepository
) {
    suspend operator fun invoke(): Result<List<String>> {
        return repository.getPdfUrls()
    }
}