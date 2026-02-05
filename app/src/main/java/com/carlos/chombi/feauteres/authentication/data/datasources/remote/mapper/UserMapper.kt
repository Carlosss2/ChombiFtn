package com.carlos.chombi.feauteres.authentication.data.datasources.remote.mapper

import com.carlos.chombi.feauteres.authentication.data.datasources.remote.model.UserDto
import com.carlos.chombi.feauteres.authentication.data.datasources.remote.model.UserRegisterDto
import com.carlos.chombi.feauteres.authentication.domain.entities.User


fun User.toRegisterDto(): UserRegisterDto {
    return UserRegisterDto(
        name = this.name,
        lastName = this.lastName,
        email = this.email,
        password = this.password
    )
}


fun UserDto.toDomain(): User {
    return User(
        name = this.name,
        lastName = this.lastName,
        email = this.email,
        password = "" // El backend no devuelve la contraseña por seguridad
    )
}