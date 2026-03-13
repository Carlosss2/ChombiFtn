package com.carlos.chombi.feauteres.history.data.datasources.remote.mapper

import com.carlos.chombi.feauteres.history.data.datasources.remote.model.BusHistoryDto
import com.carlos.chombi.feauteres.history.domain.entities.BusHistory

// En tu mapper (toDomain)
fun BusHistoryDto.toDomain(): BusHistory {
    return BusHistory(
        id = id,
        driverName = driverName,
        licensePlate = licensePlate,
        shift = "Turno $shiftOrder",
        date = date.substringBefore("T")
    )
}

fun List<BusHistoryDto>.toDomain(): List<BusHistory> {
    return this.map { it.toDomain() }
}
