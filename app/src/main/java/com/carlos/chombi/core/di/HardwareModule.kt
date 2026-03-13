package com.carlos.chombi.core.di

import com.carlos.chombi.core.hardware.data.AndroidBiometricManager
import com.carlos.chombi.core.hardware.data.AndroidCameraManager
import com.carlos.chombi.core.hardware.data.AndroidVibratorManager
import com.carlos.chombi.core.hardware.domain.BiometricManager
import com.carlos.chombi.core.hardware.domain.CameraManager
import com.carlos.chombi.core.hardware.domain.VibratorManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HardwareModule {
    @Binds
    @Singleton
    abstract fun bindCameraManager(
        impl: AndroidCameraManager
    ): CameraManager

    @Binds
    @Singleton
    abstract fun bindBiometricManager(
        impl: AndroidBiometricManager
    ): BiometricManager

    @Binds
    @Singleton
    abstract fun bindVibratorManager(
        impl: AndroidVibratorManager
    ): VibratorManager
}