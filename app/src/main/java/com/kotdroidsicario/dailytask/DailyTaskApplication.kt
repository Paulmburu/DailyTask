package com.kotdroidsicario.dailytask

import android.app.Application
import com.kotdroidsicario.dailytask.data.source.ITasksRepository
import com.kotdroidsicario.dailytask.data.source.TasksRepository
import com.kotdroidsicario.dailytask.util.scheduleDailyTasksAlarms
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber

class DailyTaskApplication : Application(){

    val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val taskRepository: ITasksRepository
        get() = ServiceLocator.provideTasksRepository(this)

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        scheduleDailyTasksAlarms(this, taskRepository as TasksRepository, applicationScope)
    }
}