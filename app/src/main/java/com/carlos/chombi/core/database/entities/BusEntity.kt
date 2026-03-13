package com.carlos.chombi.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bus_table")
data class BusEntity(
    @PrimaryKey
    val id: String,
    val licencePlate: String,
    val driver: String,
    val unitNumber: Int,
    val shift: String,
    val isWorking: Boolean,
    val model: String,
    val imageUrl: String? = null
)