package com.mcaps.mmm.data.repository

import com.mcaps.mmm.data.api.response.QuestionPrefResponse
import com.mcaps.mmm.data.api.retrofit.ApiService

class QuestionPrefRepository private constructor(private val apiService: ApiService) {
    suspend fun getAllQuestionPref(): QuestionPrefResponse {
        return apiService.getPrefQuestion()
    }

    suspend fun getQuestionPref1(): QuestionPrefResponse {
        return apiService.getPrefQuestion1()
    }

    suspend fun getQuestionPref2(): QuestionPrefResponse {
        return apiService.getPrefQuestion2()
    }

    suspend fun getQuestionPref3(): QuestionPrefResponse {
        return apiService.getPrefQuestion3()
    }

    suspend fun getQuestionPref4(): QuestionPrefResponse {
        return apiService.getPrefQuestion4()
    }

    companion object {
        @Volatile
        private var instance: QuestionPrefRepository? = null

        fun getInstance(apiService: ApiService): QuestionPrefRepository =
            instance ?: synchronized(this) {
                instance ?: QuestionPrefRepository(apiService)
            }.also { instance = it }
    }
}