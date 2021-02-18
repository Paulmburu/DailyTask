package com.kotdroidsicario.dailytask.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.kotdroidsicario.dailytask.R
import com.kotdroidsicario.dailytask.data.source.TasksRepository
import com.kotdroidsicario.dailytask.notifications.AlarmReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

fun scheduleDailyTasksAlarms(
    context: Context,
    tasksRepository: TasksRepository,
    coroutineScope: CoroutineScope
) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    setAlarm(
        context,
        context.getString(R.string.delete_tasks_title),
        context.getString(R.string.delete_tasks_description),
        23,
        59,
        Integer.MAX_VALUE,
        alarmManager,
        AlarmType.REAPETING
    )

    coroutineScope.launch {
        val availableTasks = tasksRepository.observeTasks()

        availableTasks.collect { tasks ->

            for ((index, task) in tasks.withIndex()) {
                val taskTimeline = Date().run {
                    hours = task.hours
                    minutes = task.minutes
                    time
                }

                if (taskTimeline > System.currentTimeMillis()) {
                    setAlarm(
                        context,
                        task.title,
                        task.description,
                        task.hours,
                        task.minutes,
                        index,
                        alarmManager,
                        AlarmType.EXACT
                    )
                }
            }
        }
    }
}

fun setAlarm(
    context: Context,
    title: String,
    description: String,
    hours: Int,
    minutes: Int,
    requestCode: Int,
    alarmManager: AlarmManager,
    alarmType: AlarmType
) {
    //Intent to an Alarm Broadcast Receiver` handling notifications
    val intent = Intent(context, AlarmReceiver::class.java)
    intent.putExtra("title", title)
    intent.putExtra("description", description)
    intent.putExtra("alarmtype", alarmType.value)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        requestCode,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )


    val calendar: Calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.HOUR_OF_DAY, hours)
        set(Calendar.MINUTE, minutes)
    }

    if(alarmType == AlarmType.EXACT) {
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }else if(alarmType == AlarmType.REAPETING){
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,//Repeat daily
            pendingIntent
        )
    }
}


enum class AlarmType(val value: Int){
    REAPETING(0) , EXACT(1)
}