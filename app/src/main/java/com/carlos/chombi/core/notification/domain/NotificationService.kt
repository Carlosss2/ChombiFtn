package com.carlos.chombi.core.notification.domain

interface NotificationService {
    fun showNotification(title: String, content: String)
}
