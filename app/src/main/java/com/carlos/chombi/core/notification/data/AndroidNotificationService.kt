package com.carlos.chombi.core.notification.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.carlos.chombi.MainActivity
import com.carlos.chombi.R
import com.carlos.chombi.core.notification.domain.NotificationService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.random.Random

class AndroidNotificationService @Inject constructor(
    @ApplicationContext private val context: Context
) : NotificationService {

    private val notifier = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val channelId = "chombi_local_alerts"

    override fun showNotification(title: String, content: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val alertChannel = NotificationChannel(
                channelId,
                "Notificaciones de Chombi",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notifier.createNotificationChannel(alertChannel)
        }

        val launchIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val pendingAction = PendingIntent.getActivity(
            context, 0, launchIntent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val alertBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(content)
            .setAutoCancel(true)
            .setContentIntent(pendingAction)

        notifier.notify(Random.nextInt(), alertBuilder.build())
    }
}
