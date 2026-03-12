package com.carlos.chombi.feauteres.authentication.data.datasources.remote.model

import com.google.gson.annotations.SerializedName

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


data class AuthResponse(
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("last_name") val lastName: String?,
    val email: String?,
    @SerializedName("role_name") val roleName: String?,
    val token: String?
)