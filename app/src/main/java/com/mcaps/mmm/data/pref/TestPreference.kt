package com.mcaps.mmm.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.testDataStore: DataStore<Preferences> by preferencesDataStore(name = "test_preferences")

class TestPreference private constructor(private val dataStore: DataStore<Preferences>) {

    private val SCORES_KEY = stringPreferencesKey("scores")
    private val MINAT_KEY = stringPreferencesKey("minat")
    private val QUIZ1_KEY = intPreferencesKey("quiz1")
    private val QUIZ2_KEY = intPreferencesKey("quiz2")
    private val QUIZ3_KEY = intPreferencesKey("quiz3")
    private val QUIZ4_KEY = intPreferencesKey("quiz4")

    suspend fun saveScores(scores: List<Int>) {
        val scoresString = scores.joinToString(",")
        dataStore.edit { preferences ->
            preferences[SCORES_KEY] = scoresString
        }
    }

    fun getScores(): Flow<List<Int>> {
        return dataStore.data.map { preferences ->
            val scoresString = preferences[SCORES_KEY] ?: ""
            if (scoresString.isNotEmpty()) {
                scoresString.split(",").map { it.toIntOrNull() ?: 0 }
            } else {
                emptyList()
            }
        }
    }

    suspend fun saveMinat(minat: List<Int>) {
        val minatString = minat.joinToString(",")
        dataStore.edit { preferences ->
            preferences[MINAT_KEY] = minatString
        }
    }

    fun getMinat(): Flow<List<Int>> {
        return dataStore.data.map { preferences ->
            val minatString = preferences[MINAT_KEY] ?: ""
            if (minatString.isNotEmpty()) {
                minatString.split(",").map { it.toIntOrNull() ?: 0 }
            } else {
                emptyList()
            }
        }
    }

    suspend fun saveQuiz(quizKey: Preferences.Key<Int>, value: Int) {
        dataStore.edit { preferences ->
            preferences[quizKey] = value
        }
    }

    fun getQuiz(quizKey: Preferences.Key<Int>): Flow<Int> {
        return dataStore.data.map { preferences ->
            preferences[quizKey] ?: 0
        }
    }

    companion object {
        val QUIZ1_KEY = intPreferencesKey("quiz1")
        val QUIZ2_KEY = intPreferencesKey("quiz2")
        val QUIZ3_KEY = intPreferencesKey("quiz3")
        val QUIZ4_KEY = intPreferencesKey("quiz4")

        @Volatile
        private var INSTANCE: TestPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): TestPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = TestPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
