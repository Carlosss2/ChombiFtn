package com.carlos.chombi.feauteres.history.data.datasources.remote.model

import com.google.gson.annotations.SerializedName


data class BusHistoryResponse(
    @SerializedName("vehicle_history")
    val history: List<BusHistoryDto>
)

data class BusHistoryDto(
    @SerializedName("id") val id: String,
    @SerializedName("driver_name") val driverName: String,
    @SerializedName("license_plate") val licensePlate: String,
    @SerializedName("shift_order") val shiftOrder: Int,
    @SerializedName("date") val date: String
)
