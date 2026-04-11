package com.carlos.chombi.feauteres.busManagement.data.repositories

import com.carlos.chombi.core.network.ChombiApi
import com.carlos.chombi.feauteres.busManagement.data.datasources.remote.mapper.toDto
import com.carlos.chombi.feauteres.busManagement.domain.entities.Bus
import com.carlos.chombi.feauteres.busManagement.domain.repositories.BusRepository
import com.carlos.chombi.feauteres.busManagement.data.datasources.remote.mapper.toDomain
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class BusRepositoryImpl @Inject constructor(
    private val api: ChombiApi
) : BusRepository {

    override suspend fun getAllBuses(): Result<List<Bus>> {
        return try {
            val response = api.getAllBuses()
            Result.success(response.vehicles.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addBus(bus: Bus): Result<Unit> {
        return try {
            val imagePath = bus.imageUrl ?: return Result.failure(Exception("La fotografía es obligatoria"))
            val file = File(imagePath)

            if (!file.exists()) {
                return Result.failure(Exception("El archivo de imagen no se encontró."))
            }

            // Convertimos los datos de texto
            val textMediaType = "text/plain".toMediaTypeOrNull()
            val driverName = bus.driver.toRequestBody(textMediaType)
            val licensePlate = bus.licencePlate.toRequestBody(textMediaType)
            val unitNumber = bus.unitNumber.toString().toRequestBody(textMediaType)
            val model = bus.model.toRequestBody(textMediaType)
            val shift = "1".toRequestBody(textMediaType) // API Go espera 1 por defecto
            val isWorking = "true".toRequestBody(textMediaType)

            // Preparamos la imagen multipart
            val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imagePart = MultipartBody.Part.createFormData("image", file.name, requestFile)

            api.addBus(
                driverName = driverName,
                licensePlate = licensePlate,
                unitNumber = unitNumber,
                model = model,
                shift = shift,
                isWorking = isWorking,
                image = imagePart
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateBus(bus: Bus): Result<Unit> {
        return try {

            api.updateBus(id = bus.id, bus = bus.toDto())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteBusById(id: String): Result<Unit> {
        return try {
            api.deleteBus(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    override suspend fun getBusByUnitNumber(unitNumber: Int): Result<Bus> {
        return try {
            val response = api.getBusByUnitNumber(unitNumber)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}