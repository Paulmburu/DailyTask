package com.kotdroidsicario.dailytask.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.kotdroidsicario.dailytask.DailyTaskApplication
import com.kotdroidsicario.dailytask.R
import com.kotdroidsicario.dailytask.util.AlarmType
import com.kotdroidsicario.dailytask.util.makeStatusNotification
import kotlinx.coroutines.launch
import timber.log.Timber

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, p1: Intent?) {
        val title = p1?.getStringExtra("title")
        val description = p1?.getStringExtra("description")
        val alarmType = p1?.getIntExtra("alarmtype", AlarmType.EXACT.value)

        Timber.tag("AlarmReceiver").d("${title} : ${description} : ${alarmType}")

        if (alarmType == AlarmType.REAPETING.value) {
            (context.applicationContext as DailyTaskApplication).run {
                applicationScope.launch {
                    taskRepository.deleteAllTasks()
                }
            }
        } else {
            if (!title.isNullOrEmpty()) {
                description?.let {
                    makeStatusNotification(
                        title,
                        description,
                        context
                    )
                }
            } else
                makeStatusNotification(
                    context.getString(R.string.anonymous_name),
                    context.getString(R.string.anonymous_description),
                    context
                )
        }
    }

}