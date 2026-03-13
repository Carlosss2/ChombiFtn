package com.carlos.chombi.feauteres.home.domain.entities

data class Bus(
    val id: String,
    val licencePlate: String,
    val driver: String,
    val unitNumber: Int,
    val shift: String,
    val isWorking: Boolean,
    val model: String = "",
    val imageUrl: String? = null
)
