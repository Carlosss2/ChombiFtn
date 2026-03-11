package com.carlos.chombi.core.hardware.domain

import java.io.File

interface CameraManager {

    fun hasCamera(): Boolean

    suspend fun takePhoto(): Result<File>
}