package com.carlos.chombi.feauteres.home.data.datasources.remote.mapper

import com.carlos.chombi.core.database.entities.BusEntity
import com.carlos.chombi.feauteres.busManagement.data.datasources.remote.model.BusDto
import com.carlos.chombi.feauteres.home.domain.entities.Bus

fun BusDto.toDomain(): Bus {
    return Bus(
        id = id,
        licencePlate = licencePlate,
        driver = driver,
        unitNumber = unitNumber,
        shift = shift.toString(),
        isWorking = isWorking,
        model = model,
        imageUrl = imageUrl
    )
}

fun BusDto.toEntity(): BusEntity {
    return BusEntity(
        id = id,
        licencePlate = licencePlate,
        driver = driver,
        unitNumber = unitNumber,
        shift = shift.toString(),
        isWorking = isWorking,
        model = model,
        imageUrl = imageUrl
    )
}

fun List<BusDto>.toDomain(): List<Bus> {
    return map { it.toDomain() }
}
