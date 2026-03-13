package com.carlos.chombi.feauteres.history.domain.entities


data class BusHistory(
    val id: String,
    val driverName : String,
    val licensePlate: String,
    val shift: String,
    val date: String
)
