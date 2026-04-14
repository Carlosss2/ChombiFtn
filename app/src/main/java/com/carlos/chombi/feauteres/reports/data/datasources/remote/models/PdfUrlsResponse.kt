package com.carlos.chombi.feauteres.reports.data.datasources.remote.models

import com.google.gson.annotations.SerializedName

data class PdfUrlsResponse(
    @SerializedName("pdf_urls")
    val pdfUrls: List<String>
)