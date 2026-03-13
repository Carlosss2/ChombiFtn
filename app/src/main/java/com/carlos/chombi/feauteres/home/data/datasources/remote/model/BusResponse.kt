package com.carlos.chombi.feauteres.home.data.datasources.remote.model

import com.google.gson.annotations.SerializedName

data class BusResponse(
    @SerializedName("vehicles")
    val vehicles: List<BusDto>
)

data class BusDto(
    @SerializedName("id") val id: String,
    @SerializedName("license_plate") val licencePlate: String,
    @SerializedName("driver_name") val driver: String,
    @SerializedName("unit_number") val unitNumber: Int,
    @SerializedName("shift") val shift: Int,
    @SerializedName("is_working") val isWorking: Boolean,
    @SerializedName("model") val model: String = "",
    @SerializedName("image_url") val imageUrl: String? = null
)