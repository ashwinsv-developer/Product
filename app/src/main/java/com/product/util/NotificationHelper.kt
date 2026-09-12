package com.product.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.product.R

object NotificationHelper {
    // Changing the channel ID to a brand new one ensures the system creates it with HIGH importance fresh
    private const val CHANNEL_ID = "app_usage_heads_up_channel_v50"
    private const val CHANNEL_NAME = "App Usage Notifications"

    fun showUsageNotification(context: Context, minutes: Int) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Shows high priority heads-up banners for app usage updates"
                enableLights(true)
                enableVibration(true)
                setShowBadge(true)
                lockscreenVisibility = android.app.Notification.VISIBILITY_PUBLIC
            }
            notificationManager.createNotificationChannel(channel)
        }

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("App Usage Update")
            .setContentText("You have been using the application for $minutes minutes.")
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Fallback standard icon to ensure it renders correctly
            .setPriority(NotificationCompat.PRIORITY_MAX)   // MAX priority triggers heads-up banner display
            .setSound(defaultSoundUri)                      // Sound is required for heads-up display
            .setVibrate(longArrayOf(0, 250, 250, 250))      // Vibration pattern helps force heads-up display
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)
            .build()

        // Generate a random unique notification ID for each display to ensure multiple separate notifications show up concurrently
        val uniqueNotificationId = (1..100000000).random()
        notificationManager.notify(uniqueNotificationId, notification)
    }
}
