package com.carlos.chombi.feauteres.authentication.data.datasources.remote.model

// Para enviar el registro
data class UserRegisterDto(
    val name: String,
    val lastName: String,
    val email: String,
    val password: String
)

// Para enviar el login (Nuevo)
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