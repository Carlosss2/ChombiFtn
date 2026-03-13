package com.carlos.chombi.feauteres.home.data.datasources.remote.mapper

// 🚨 IMPORTANTE: Cambiamos este import para usar el DTO que devuelve la API
import com.carlos.chombi.feauteres.busManagement.data.datasources.remote.model.BusDto
// El modelo de dominio sí sigue siendo el de home
import com.carlos.chombi.feauteres.home.domain.entities.Bus

fun BusDto.toDomain(): Bus {
    return Bus(
        id = id,
        licencePlate = licencePlate,
        driver = driver,
        unitNumber = unitNumber,
        shift = shift.toString(), // Lo pasamos a String para la UI de Home
        isWorking = isWorking,
        model = model,
        imageUrl = imageUrl
    )
}

// Puedes borrar la función toDto() de aquí si no vas a enviar datos al backend desde Home

fun List<BusDto>.toDomain(): List<Bus> {
    return map { it.toDomain() }
}