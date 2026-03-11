package com.carlos.chombi.core.hardware.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.hardware.camera2.*
import android.media.ImageReader
import android.os.Handler
import android.os.Looper
import com.carlos.chombi.core.hardware.domain.CameraManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import kotlin.coroutines.resume

class AndroidCameraManager @Inject constructor(
    @ApplicationContext private val context: Context
) : CameraManager {

    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as android.hardware.camera2.CameraManager
    private val mainHandler = Handler(Looper.getMainLooper())

    override fun hasCamera(): Boolean =
        context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)

    @SuppressLint("MissingPermission")
    override suspend fun takePhoto(): Result<File> = suspendCancellableCoroutine { continuation ->
        if (!hasCamera()) {
            continuation.resume(Result.failure(Exception("El dispositivo no tiene cámara")))
            return@suspendCancellableCoroutine
        }

        try {
            // Buscamos la cámara trasera (o la primera disponible)
            val cameraId = cameraManager.cameraIdList.firstOrNull { id ->
                val chars = cameraManager.getCameraCharacteristics(id)
                chars.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_BACK
            } ?: cameraManager.cameraIdList.firstOrNull()

            if (cameraId == null) {
                continuation.resume(Result.failure(Exception("No se encontró ninguna cámara")))
                return@suspendCancellableCoroutine
            }

            // Configuramos un lector de imágenes invisible
            val imageReader = ImageReader.newInstance(1920, 1080, ImageFormat.JPEG, 1)
            var cameraDevice: CameraDevice? = null

            // Función para liberar los recursos
            fun closeCamera() {
                cameraDevice?.close()
                imageReader.close()
            }

            // Cancelar el proceso si la corrutina muere prematuramente
            continuation.invokeOnCancellation { closeCamera() }

            // Listener que se dispara cuando la foto se ha capturado físicamente
            imageReader.setOnImageAvailableListener({ reader ->
                val image = reader.acquireLatestImage()
                if (image != null) {
                    try {
                        val buffer = image.planes[0].buffer
                        val bytes = ByteArray(buffer.remaining())
                        buffer.get(bytes)

                        // Guardamos la foto en la caché interna de la app
                        val file = File(context.cacheDir, "photo_${System.currentTimeMillis()}.jpg")
                        FileOutputStream(file).use { it.write(bytes) }

                        // Devolvemos el archivo a la capa de dominio
                        if (continuation.isActive) continuation.resume(Result.success(file))
                    } catch (e: Exception) {
                        if (continuation.isActive) continuation.resume(Result.failure(e))
                    } finally {
                        image.close()
                        closeCamera()
                    }
                }
            }, mainHandler)

            // Callback que maneja el ciclo de vida de la apertura de la cámara
            val stateCallback = object : CameraDevice.StateCallback() {
                override fun onOpened(camera: CameraDevice) {
                    cameraDevice = camera
                    try {
                        val surfaces = listOf(imageReader.surface)
                        // Creamos la sesión de captura
                        camera.createCaptureSession(surfaces, object : CameraCaptureSession.StateCallback() {
                            override fun onConfigured(session: CameraCaptureSession) {
                                try {
                                    // Solicitamos tomar una foto fija
                                    val captureRequest = camera.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
                                    captureRequest.addTarget(imageReader.surface)
                                    session.capture(captureRequest.build(), null, mainHandler)
                                } catch (e: Exception) {
                                    closeCamera()
                                    if (continuation.isActive) continuation.resume(Result.failure(e))
                                }
                            }

                            override fun onConfigureFailed(session: CameraCaptureSession) {
                                closeCamera()
                                if (continuation.isActive) continuation.resume(Result.failure(Exception("Fallo al configurar la sesión de cámara")))
                            }
                        }, mainHandler)
                    } catch (e: Exception) {
                        closeCamera()
                        if (continuation.isActive) continuation.resume(Result.failure(e))
                    }
                }

                override fun onDisconnected(camera: CameraDevice) {
                    closeCamera()
                    if (continuation.isActive) continuation.resume(Result.failure(Exception("Cámara desconectada")))
                }

                override fun onError(camera: CameraDevice, error: Int) {
                    closeCamera()
                    if (continuation.isActive) continuation.resume(Result.failure(Exception("Error en cámara: Código $error")))
                }
            }

            // Finalmente, pedimos a Android que abra la cámara
            cameraManager.openCamera(cameraId, stateCallback, mainHandler)

        } catch (e: Exception) {
            if (continuation.isActive) continuation.resume(Result.failure(e))
        }
    }
}