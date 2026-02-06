package com.carlos.chombi.feauteres.busManagement.data.datasources.remote.model


data class BusResponse(
    val vehicles: List<BusDto>
)


data class BusDto(
    val id: Int,
    val licencePlate: String,
    val driver: String,
    val unitNumber: Int,
    val shift: String,
    val isWorking: Boolean
)
