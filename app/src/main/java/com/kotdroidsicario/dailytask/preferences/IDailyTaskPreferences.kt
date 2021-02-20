package com.kotdroidsicario.dailytask.preferences

import kotlinx.coroutines.flow.Flow

interface IDailyTaskPreferences {
    val getUsername: Flow<String>
    val firstTimeAppLaunchStatus: Flow<Boolean>
    suspend fun saveUsername(username: String)
    suspend fun updateFirstTimeAppLaunch(launched: Boolean)
    suspend fun resetPrefs()
}