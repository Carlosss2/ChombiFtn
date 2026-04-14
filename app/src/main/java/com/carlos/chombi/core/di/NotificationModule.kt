package com.carlos.chombi.core.di

import com.carlos.chombi.core.notification.data.AndroidNotificationService
import com.carlos.chombi.core.notification.domain.NotificationService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationModule {

    @Binds
    @Singleton
    abstract fun bindNotificationService(
        notificationService: AndroidNotificationService
    ): NotificationService
}
