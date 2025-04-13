package com.example.appfood.model.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(context: Context) {
    private val appDataStore = context.dataStore

    companion object {
        private val KEY_IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val KEY_FIRST_LAUNCH = booleanPreferencesKey("is_first_launch")

    }

    // Flow to get the login state
    val isLoggedIn: Flow<Boolean> = appDataStore.data
        .map { preferences -> preferences[KEY_IS_LOGGED_IN] ?: false }

    // Save the login state
    suspend fun saveLoginState(loggedIn: Boolean) {
        appDataStore.edit { preferences ->
            preferences[KEY_IS_LOGGED_IN] = loggedIn
        }
    }

    // Mặc định là true -> lần đầu tiên mở app
    val isFirstLaunch: Flow<Boolean> = appDataStore.data
        .map { preferences -> preferences[KEY_FIRST_LAUNCH] ?: true }

    suspend fun setFirstLaunchCompleted() {
        appDataStore.edit { preferences ->
            preferences[KEY_FIRST_LAUNCH] = false
        }
    }


}
