package com.carlos.chombi.core.hardware.domain

interface VibratorManager {
    fun vibrate(durationMillis: Long)
    fun stop()
}