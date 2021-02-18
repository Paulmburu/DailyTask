package com.kotdroidsicario.dailytask.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kotdroidsicario.dailytask.R
import com.kotdroidsicario.dailytask.tasks.MainActivity

/**
 * Create a Notification
 *
 * @param message Message shown on the notification
 * @param context Context needed to create Toast
 */
fun makeStatusNotification(notificationTitle: String, message: String, context: Context) {
    val notificationIntent = Intent(context, MainActivity::class.java)
    val notificationPendingIntent = PendingIntent.getActivity(
        context,
        NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT
    )

    // Make a channel if necessary
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = DAILY_TASK_NOTIFICATION_CHANNEL_NAME
        val description = DAILY_TASK_NOTIFICATION_CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = description

        // Add the channel
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        notificationManager?.createNotificationChannel(channel)
    }

    // Create the notification
    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_task)
        .setContentTitle(notificationTitle)
        .setContentText(message)
        .setContentIntent(notificationPendingIntent)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0))

    // Show the notification
    NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
}