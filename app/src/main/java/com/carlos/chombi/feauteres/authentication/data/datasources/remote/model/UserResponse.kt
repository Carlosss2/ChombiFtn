package com.carlos.chombi.feauteres.authentication.data.datasources.remote.model

// Esto representa el JSON exacto que recibes o envías a la API
data class UserDto(
    val name: String?,
    val lastName: String?,
    val email: String?,
    val password: String? // Nota: Usualmente el backend no devuelve el password, pero lo dejo para que coincida con tu entidad actual.
)