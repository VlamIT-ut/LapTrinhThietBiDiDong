package com.example.appfood.model.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.stringPreferencesKey

private val Context.dataStore by preferencesDataStore(name = "user_prefs")
class UserPreferences(context: Context) {
    private val appDataStore = context.dataStore

    companion object {
        private val KEY_IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val KEY_FIRST_LAUNCH = booleanPreferencesKey("is_first_launch")
        private val KEY_APP_LANGUAGE = stringPreferencesKey("app_language")
    }

    val isLoggedIn: Flow<Boolean> = appDataStore.data
        .map { preferences -> preferences[KEY_IS_LOGGED_IN] ?: false }

    suspend fun saveLoginState(loggedIn: Boolean) {
        appDataStore.edit { preferences ->
            preferences[KEY_IS_LOGGED_IN] = loggedIn
        }
    }

    val isFirstLaunch: Flow<Boolean> = appDataStore.data
        .map { preferences -> preferences[KEY_FIRST_LAUNCH] ?: true }

    suspend fun setFirstLaunchCompleted() {
        appDataStore.edit { preferences ->
            preferences[KEY_FIRST_LAUNCH] = false
        }
    }

    val appLanguage: Flow<String> = appDataStore.data
        .map { preferences -> preferences[KEY_APP_LANGUAGE] ?: "en" }

    suspend fun setAppLanguage(lang: String) {
        appDataStore.edit { preferences ->
            preferences[KEY_APP_LANGUAGE] = lang
        }
    }
}
