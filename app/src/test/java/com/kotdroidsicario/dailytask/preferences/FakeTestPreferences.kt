package com.kotdroidsicario.dailytask.preferences

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTestPreferences: IDailyTaskPreferences {
    var username = DEFAULT_USERNAME
    var firstTimeAppLaunchedStatus = DEFAULT_APP_LAUNCHED_STATUS

    override val getUsername: Flow<String>
        get() = flow { emit(username) }

    override val firstTimeAppLaunchStatus: Flow<Boolean>
        get() = flow { emit(firstTimeAppLaunchedStatus) }

    override suspend fun saveUsername(username: String) {
        this.username = username
    }

    override suspend fun updateFirstTimeAppLaunch(launched: Boolean) {
        this.firstTimeAppLaunchedStatus = launched
    }

    override suspend fun resetPrefs() {
        username = DEFAULT_USERNAME
        firstTimeAppLaunchedStatus = DEFAULT_APP_LAUNCHED_STATUS
    }

    companion object{
        val DEFAULT_USERNAME = "Anonymous"
        val DEFAULT_APP_LAUNCHED_STATUS = true
    }
}