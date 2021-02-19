package com.kotdroidsicario.dailytask.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class DailyTaskPreferences(private val context: Context) {

    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = "daily_task_pref"
    )

    companion object{
        val USERNAME = stringPreferencesKey(name = "username")
        val FIRST_TIME_APP_LAUNCH = booleanPreferencesKey(name = "first_time")
    }

    suspend fun saveUsername(username: String){
        dataStore.edit { preferences ->
            preferences[USERNAME] = username
        }
    }

    suspend fun updateFirstTimeAppLaunch(launched: Boolean){
        dataStore.edit { preferences ->
            preferences[FIRST_TIME_APP_LAUNCH] = launched
        }
    }

    val getUsername: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[USERNAME] ?: "Anonymous"
        }

    val firstTimeAppLaunchStatus: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[FIRST_TIME_APP_LAUNCH] ?: true
        }

    suspend fun resetPrefs(){
        saveUsername("Anonymous")
        updateFirstTimeAppLaunch(true)
    }
}