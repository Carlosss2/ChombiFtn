package com.carlos.chombi.feauteres.busManagement.data.datasources.remote.model

import com.google.gson.annotations.SerializedName

data class BusResponse(
    @SerializedName("vehicles")
    val vehicles: List<BusDto>
)

data class BusDto(
    @SerializedName("id") val id: String,
    @SerializedName("license_plate") val licencePlate: String, // La API dice license_plate
    @SerializedName("driver_name") val driver: String,         // La API dice driver_name
    @SerializedName("unit_number") val unitNumber: Int,        // La API dice unit_number
    @SerializedName("shift") val shift: Int,                   // OJO: La API manda un Int
    @SerializedName("is_working") val isWorking: Boolean,      // La API dice is_working
    @SerializedName("model") val model: String = "",
    @SerializedName("image_url") val imageUrl: String? = null  // La API dice image_url
)