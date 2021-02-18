package com.kotdroidsicario.dailytask.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.kotdroidsicario.dailytask.R
import com.kotdroidsicario.dailytask.util.makeStatusNotification

class BackupDataWorker(
    private val appContext: Context,
    params: WorkerParameters
): Worker(appContext, params) {

    companion object {
        const val WORK_NAME = "BackupDataWorker"
    }
    override fun doWork(): Result {
        return try {
            makeStatusNotification(appContext.getString(R.string.backup_title),
                appContext.getString(R.string.backup_simulation),
                appContext
            )
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}