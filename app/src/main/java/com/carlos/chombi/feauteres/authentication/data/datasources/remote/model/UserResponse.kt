    package com.carlos.chombi.feauteres.authentication.data.datasources.remote.model

    data class UserRegisterDto(
        val first_name: String,
        val last_name: String,
        val email: String,
        val password: String,
        val rol_id: String
    )

    data class LoginRequestDto(
        val email: String,
        val password: String
    )

    // Lo que recibes del servidor (Login y Registro suelen devolver lo mismo)
    data class AuthResponse(
        val data: UserDto,
        val token: String
    )

    // El usuario que viene DENTRO de la respuesta
    data class UserDto(
        val name: String,
        val lastName: String,
        val email: String
    )