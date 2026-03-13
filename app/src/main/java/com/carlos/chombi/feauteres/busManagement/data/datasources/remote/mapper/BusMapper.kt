package com.carlos.chombi.feauteres.busManagement.data.datasources.remote.mapper

import com.carlos.chombi.feauteres.busManagement.data.datasources.remote.model.BusDto
import com.carlos.chombi.feauteres.busManagement.domain.entities.Bus

fun BusDto.toDomain(): Bus {
    return Bus(
        id = id,
        licencePlate = licencePlate,
        driver = driver,
        unitNumber = unitNumber,
        shift = shift.toString(), // Lo pasamos a String para tu UI
        isWorking = isWorking,
        model = model,
        imageUrl = imageUrl
    )
}

fun Bus.toDto(): BusDto {
    return BusDto(
        id = id,
        licencePlate = licencePlate,
        driver = driver,
        unitNumber = unitNumber,
        shift = shift.toIntOrNull() ?: 1, // Lo regresamos a Int para la API
        isWorking = isWorking,
        model = model,
        imageUrl = imageUrl
    )
}

fun List<BusDto>.toDomain(): List<Bus> {
    return map { it.toDomain() }
}