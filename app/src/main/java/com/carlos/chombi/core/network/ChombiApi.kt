package com.carlos.chombi.core.network

import com.carlos.chombi.feauteres.authentication.data.datasources.remote.model.UserDto
import retrofit2.http.Body
import retrofit2.http.POST

interface ChombiApi {
    @POST("/login")
    suspend fun loginUser(
        @Body userCredentials: UserDto // Enviamos el objeto con email y pass
    ): UserDto // Recibimos el usuario logueado


    @POST("/register") //
    suspend fun registerUser(
        @Body userData: UserDto // Enviamos nombre, apellido, email, pass
    ): UserDto // Recibimos el usuario creado
}