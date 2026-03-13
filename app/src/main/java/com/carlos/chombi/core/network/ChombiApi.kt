package com.carlos.chombi.core.network

import com.carlos.chombi.feauteres.authentication.data.datasources.remote.model.*
import com.carlos.chombi.feauteres.busManagement.data.datasources.remote.model.BusDto
import com.carlos.chombi.feauteres.busManagement.data.datasources.remote.model.BusResponse
import com.carlos.chombi.feauteres.history.data.datasources.remote.model.BusHistoryResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ChombiApi {

    @POST("auth/login")
    suspend fun loginUser(
        @Body credentials: LoginRequestDto // ENVIAMOS email y pass
    ): AuthResponse // RECIBIMOS usuario + token

    @POST("auth/register")
    suspend fun registerUser(
        @Body userData: UserRegisterDto // ENVIAMOS datos completos
    ): retrofit2.Response<AuthResponse>

    @GET("vehicles")
    suspend fun getAllBuses(): BusResponse
    @Multipart
    @POST("vehicles")
    suspend fun addBus(
        @Part("driver_name") driverName: okhttp3.RequestBody,
        @Part("license_plate") licensePlate: okhttp3.RequestBody,
        @Part("unit_number") unitNumber: okhttp3.RequestBody,
        @Part("model") model: okhttp3.RequestBody,
        @Part("shift") shift: okhttp3.RequestBody,
        @Part("is_working") isWorking: okhttp3.RequestBody,
        @Part image: okhttp3.MultipartBody.Part
    )
    @GET("vehicles/history")
    suspend fun getBusHistory(): BusHistoryResponse


    @PUT("vehicles/{id}")
    suspend fun updateBus(
        @Path("id") id: Int,
        @Body bus: BusDto
    )

    @DELETE("vehicles/{id}")
    suspend fun deleteBus(@Path("id") id: Int)
}