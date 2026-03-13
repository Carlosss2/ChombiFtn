package com.carlos.chombi.core.hardware.data

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager as OsVibratorManager
import com.carlos.chombi.core.hardware.domain.VibratorManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class AndroidVibratorManager @Inject constructor(
    @ApplicationContext private val context: Context
) : VibratorManager {

    private val vibrator: Vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as OsVibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    override fun vibrate(durationMillis: Long) {
        if (vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Para Android 8.0 o superior
                vibrator.vibrate(VibrationEffect.createOneShot(durationMillis, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                // Para versiones más antiguas
                @Suppress("DEPRECATION")
                vibrator.vibrate(durationMillis)
            }
        }
    }

    override fun stop() {
        vibrator.cancel()
    }
}