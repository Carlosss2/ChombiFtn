package com.carlos.chombi.core.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.carlos.chombi.MainActivity
import com.carlos.chombi.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class ChombiMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(pushMessage: RemoteMessage) {
        super.onMessageReceived(pushMessage)
        
        Log.d("FCM_CHOMBI", "Mensaje recibido de: ${pushMessage.from}")
        
        pushMessage.notification?.let {
            Log.d("FCM_CHOMBI", "Cuerpo notificación: ${it.body}")
            dispatchSystemNotification(
                title = it.title ?: "Aviso Chombi",
                body = it.body ?: "Nueva actualización disponible"
            )
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Log para que el desarrollador pueda copiar el token fácilmente
        Log.d("FCM_CHOMBI", "-------------------------------------------------------")
        Log.d("FCM_CHOMBI", "TOKEN DE PRUEBA: $token")
        Log.d("FCM_CHOMBI", "-------------------------------------------------------")
    }

    private fun dispatchSystemNotification(title: String, body: String) {
        val notifier = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "chombi_alerts_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val alertChannel = NotificationChannel(
                channelId,
                "Alertas de Chombi",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notificaciones críticas de la operación"
            }
            notifier.createNotificationChannel(alertChannel)
        }

        val launchIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        
        val pendingAction = PendingIntent.getActivity(
            this, 0, launchIntent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val alertBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingAction)

        notifier.notify(Random.nextInt(), alertBuilder.build())
    }
}
