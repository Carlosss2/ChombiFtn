package com.carlos.chombi.feauteres.busManagement.domain.entities


data class Bus(
    val id: Int,
    val licencePlate: String,
    val driver: String,
    val unitNumber: Int,
    val shift: String,
    val isWorking: Boolean
)
