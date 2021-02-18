package com.kotdroidsicario.dailytask

import android.app.Application
import android.os.Build
import androidx.databinding.library.BuildConfig
import androidx.work.*
import com.kotdroidsicario.dailytask.data.source.ITasksRepository
import com.kotdroidsicario.dailytask.data.source.TasksRepository
import com.kotdroidsicario.dailytask.util.scheduleDailyTasksAlarms
import com.kotdroidsicario.dailytask.workers.BackupDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class DailyTaskApplication : Application(){

    val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val taskRepository: ITasksRepository
        get() = ServiceLocator.provideTasksRepository(this)

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        scheduleDailyTasksAlarms(this, taskRepository as TasksRepository, applicationScope)

        applicationScope.launch {
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork() {
        val repeatingRequest = PeriodicWorkRequestBuilder<BackupDataWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            BackupDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest)
    }

    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.UNMETERED)
        .setRequiresBatteryNotLow(true)
        .setRequiresCharging(true)
        .apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setRequiresDeviceIdle(true)
            }
        }.build()
}