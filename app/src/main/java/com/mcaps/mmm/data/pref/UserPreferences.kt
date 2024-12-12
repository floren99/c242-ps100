package com.mcaps.mmm.data.pref

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveSession(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = user.email
            preferences[TOKEN_KEY] = user.token
            preferences[IS_LOGIN_KEY] = true
            preferences[USERNAME_KEY] = user.username
        }
        Log.d("UserPreference", "Session saved: Email=${user.email}, Token=${user.token}, IsLogin=${true}")
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[EMAIL_KEY] ?: "",
                preferences[TOKEN_KEY] ?: "",
                preferences[IS_LOGIN_KEY] ?: false,
                preferences[USERNAME_KEY] ?: ""
            )
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
            Log.d("UserPreference", "Session cleared")
        }
    }

    suspend fun clearNotepadData() {
        dataStore.edit { preferences ->
            preferences.remove(NOTE_TOP_KEY)
            preferences.remove(NOTE_BOTTOM_KEY)
        }
        Log.d("UserPreference", "Notepad data cleared")
    }

    suspend fun saveNotepadData(topNote: String, bottomNote: String) {
        dataStore.edit { preferences ->
            preferences[NOTE_TOP_KEY] = topNote
            preferences[NOTE_BOTTOM_KEY] = bottomNote
        }
    }

    fun getNotepadData(): Flow<Pair<String, String>> {
        return dataStore.data.map { preferences ->
            val topNote = preferences[NOTE_TOP_KEY] ?: ""
            val bottomNote = preferences[NOTE_BOTTOM_KEY] ?: ""
            Pair(topNote, bottomNote)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val EMAIL_KEY = stringPreferencesKey("email")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")
        private val USERNAME_KEY = stringPreferencesKey("username")

        private val NOTE_TOP_KEY = stringPreferencesKey("note_top")
        private val NOTE_BOTTOM_KEY = stringPreferencesKey("note_bottom")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}