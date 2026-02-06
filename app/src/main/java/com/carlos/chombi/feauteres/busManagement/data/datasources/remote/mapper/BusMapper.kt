package com.carlos.chombi.feauteres.busManagement.data.datasources.remote.mapper

import com.carlos.chombi.feauteres.busManagement.data.datasources.remote.model.BusDto
import com.carlos.chombi.feauteres.busManagement.domain.entities.Bus

fun BusDto.toDomain(): Bus {
    return Bus(
        id = id,
        licencePlate = licencePlate,
        driver = driver,
        unitNumber = unitNumber,
        shift = shift,
        isWorking = isWorking
    )
}

fun Bus.toDto(): BusDto {
    return BusDto(
        id = id,
        licencePlate = licencePlate,
        driver = driver,
        unitNumber = unitNumber,
        shift = shift,
        isWorking = isWorking
    )
}
fun List<BusDto>.toDomain(): List<Bus> {
    return map { it.toDomain() }
}

