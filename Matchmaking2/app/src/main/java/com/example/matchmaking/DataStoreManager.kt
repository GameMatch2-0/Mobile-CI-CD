package com.example.matchmaking

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

class DataStoreManager(private val context: Context) {

    companion object {
        val EMAIL_KEY = stringPreferencesKey("EMAIL_KEY")
        val PASSWORD_KEY = stringPreferencesKey("PASSWORD_KEY")
        val REMEMBER_ME_KEY = booleanPreferencesKey("REMEMBER_ME_KEY")
    }

    val emailFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[EMAIL_KEY]
        }

    val passwordFlow: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[PASSWORD_KEY]
        }

    val rememberMeFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[REMEMBER_ME_KEY] ?: false
        }

    suspend fun saveCredentials(email: String, password: String, rememberMe: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = email
            preferences[PASSWORD_KEY] = password
            preferences[REMEMBER_ME_KEY] = rememberMe
        }
    }

    suspend fun clearCredentials() {
        context.dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = ""
            preferences[PASSWORD_KEY] = ""
            preferences[REMEMBER_ME_KEY] = false
        }
    }
}