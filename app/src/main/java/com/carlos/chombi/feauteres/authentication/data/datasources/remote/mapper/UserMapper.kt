package com.carlos.chombi.feauteres.authentication.data.datasources.remote.mapper

import com.carlos.chombi.feauteres.authentication.data.datasources.remote.model.AuthResponse
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


fun AuthResponse?.toDomain(): User? {
    if (this == null) return null

    return User(
        name = this.firstName ?: "",
        lastName = this.lastName ?: "",
        email = this.email ?: "",
        password = "", // El backend no devuelve la contraseña por seguridad
        roleId = this.roleName ?: "", // Mapeamos el rol de la API
    )
}