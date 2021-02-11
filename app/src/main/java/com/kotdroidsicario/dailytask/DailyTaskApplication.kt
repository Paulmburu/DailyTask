package com.kotdroidsicario.dailytask

import android.app.Application
import com.kotdroidsicario.dailytask.data.source.ITasksRepository
import timber.log.Timber

class DailyTaskApplication : Application(){

    val taskRepository: ITasksRepository
        get() = ServiceLocator.provideTasksRepository(this)

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}