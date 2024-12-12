package com.mcaps.mmm.data.repository

import com.mcaps.mmm.data.pref.UserModel
import com.mcaps.mmm.data.pref.UserPreference
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val userPreference: UserPreference
) {
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
        userPreference.clearNotepadData()
    }

    suspend fun saveNotepadData(topNote: String, bottomNote: String) {
        userPreference.saveNotepadData(topNote, bottomNote)
    }

    fun getNotepadData(): Flow<Pair<String, String>> {
        return userPreference.getNotepadData()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(userPreference: UserPreference): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference)
            }.also { instance = it }
    }
}