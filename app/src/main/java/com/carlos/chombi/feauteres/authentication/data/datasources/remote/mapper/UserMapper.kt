package com.carlos.chombi.feauteres.authentication.data.datasources.remote.mapper

import com.carlos.chombi.feauteres.authentication.data.datasources.remote.model.UserDto
import com.carlos.chombi.feauteres.authentication.domain.entities.User

fun UserDto.toDomain(): User {
    return User(
        name = name ?: "", // Manejo de nulos seguro
        lastName = lastName ?: "",
        email = email ?: "",
        password = password ?: ""
    )
}

//
fun User.toDto(): UserDto {
    return UserDto(
        name = name,
        lastName = lastName,
        email = email,
        password = password
    )
}