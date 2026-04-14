package com.carlos.chombi.feauteres.home.data.datasources.local.mapper

import com.carlos.chombi.core.database.entities.BusEntity
import com.carlos.chombi.feauteres.home.domain.entities.Bus


fun BusEntity.toDomain(): Bus {
    return Bus(
        id = id,
        licencePlate = licencePlate,
        driver = driver,
        unitNumber = unitNumber,
        shift = shift,
        isWorking = isWorking,
        model = model,
        imageUrl = imageUrl
    )
}