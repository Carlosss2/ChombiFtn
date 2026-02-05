package com.carlos.chombi.core.network

import com.carlos.chombi.feauteres.authentication.data.datasources.remote.model.*
import retrofit2.http.Body
import retrofit2.http.POST

interface ChombiApi {

    @POST("login")
    suspend fun loginUser(
        @Body credentials: LoginRequestDto // ENVIAMOS email y pass
    ): AuthResponse // RECIBIMOS usuario + token

    @POST("register")
    suspend fun registerUser(
        @Body userData: UserRegisterDto // ENVIAMOS datos completos
    ): AuthResponse // RECIBIMOS usuario + token
}