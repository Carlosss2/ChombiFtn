package com.carlos.chombi.core.network

import com.carlos.chombi.feauteres.authentication.data.datasources.remote.model.*
import com.carlos.chombi.feauteres.busManagement.data.datasources.remote.model.BusDto
import com.carlos.chombi.feauteres.busManagement.data.datasources.remote.model.BusResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ChombiApi {

    @POST("login")
    suspend fun loginUser(
        @Body credentials: LoginRequestDto // ENVIAMOS email y pass
    ): AuthResponse // RECIBIMOS usuario + token

    @POST("register")
    suspend fun registerUser(
        @Body userData: UserRegisterDto // ENVIAMOS datos completos
    ): retrofit2.Response<AuthResponse>

    @GET("vehicles")
    suspend fun getAllBuses(): BusResponse

    @POST("vehicles")
    suspend fun addBus(@Body bus: BusDto)

    @PUT("vehicles/{id}")
    suspend fun updateBus(
        @Path("id") id: Int,
        @Body bus: BusDto
    )

    @DELETE("vehicles/{id}")
    suspend fun deleteBus(@Path("id") id: Int)
}