package com.carlos.chombi.feauteres.authentication.data.datasources.remote.mapper

import com.carlos.chombi.feauteres.authentication.data.datasources.remote.model.UserDto
import com.carlos.chombi.feauteres.authentication.data.datasources.remote.model.UserRegisterDto
import com.carlos.chombi.feauteres.authentication.domain.entities.User

// Mapper de Dominio a DTO (Para enviar datos al server)
fun User.toRegisterDto(): UserRegisterDto {
    return UserRegisterDto(
        first_name = this.name,
        last_name = this.lastName,
        email = this.email,
        password = this.password,
        rol_id = this.roleId
    )
}

/**
 * Mapper de DTO a Dominio (Para recibir datos del server)
 * Se agrega el "?" en UserDto para que acepte nulos y no truene la app.
 */
fun UserDto?.toDomain(): User? {
    // Si el objeto es nulo, regresamos nulo de forma segura
    if (this == null) return null

    return User(
        name = this.name ?: "",      // Si el campo individual llega nulo, ponemos texto vacío
        lastName = this.lastName ?: "",
        email = this.email ?: "",
        password = "", // El backend no devuelve la contraseña por seguridad
        roleId = "",
    )
}