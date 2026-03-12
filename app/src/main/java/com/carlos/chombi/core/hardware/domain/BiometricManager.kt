package com.carlos.chombi.core.hardware.domain

import android.content.Context

interface BiometricManager {

    suspend fun registerBiometric(context: Context, challengeJson: String): Result<String>


    suspend fun authenticateBiometric(context: Context, challengeJson: String): Result<String>

}