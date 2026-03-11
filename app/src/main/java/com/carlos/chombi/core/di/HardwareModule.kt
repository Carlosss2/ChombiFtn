package com.carlos.chombi.core.di

import com.carlos.chombi.core.hardware.data.AndroidCameraManager
import com.carlos.chombi.core.hardware.domain.CameraManager
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
}