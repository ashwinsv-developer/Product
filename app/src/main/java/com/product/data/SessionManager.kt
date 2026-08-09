package com.product.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_session")

@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val KEY_EMAIL = stringPreferencesKey("user_email")
        private val KEY_IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }

    suspend fun saveSession(email: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_EMAIL] = email
            preferences[KEY_IS_LOGGED_IN] = true
        }
    }

    val emailFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[KEY_EMAIL]
    }

    val isLoggedInFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[KEY_IS_LOGGED_IN] ?: false
    }

    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
