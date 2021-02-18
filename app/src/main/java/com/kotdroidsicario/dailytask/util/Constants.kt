
@file:JvmName("Constants")

package com.kotdroidsicario.dailytask.util

// Notification Channel constants

// Name of Notification Channel for verbose notifications of background work
@JvmField val DAILY_TASK_NOTIFICATION_CHANNEL_NAME: CharSequence =
        "Verbose WorkManager Notifications"
const val DAILY_TASK_NOTIFICATION_CHANNEL_DESCRIPTION =
        "Shows notifications whenever a task is to be executed"
const val CHANNEL_ID = "DAILY_TASK_NOTIFICATION"
const val NOTIFICATION_ID = 1
