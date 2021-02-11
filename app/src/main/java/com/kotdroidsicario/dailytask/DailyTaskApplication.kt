package com.kotdroidsicario.dailytask

import android.app.Application
import timber.log.Timber

class DailyTaskApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}