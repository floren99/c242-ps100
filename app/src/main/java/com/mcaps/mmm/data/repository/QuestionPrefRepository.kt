package com.mcaps.mmm.data.repository

import com.mcaps.mmm.data.api.response.QuestionPrefResponse
import com.mcaps.mmm.data.api.retrofit.ApiService
import com.mcaps.mmm.view.question.model.Question

class QuestionPrefRepository private constructor(private val apiService: ApiService) {
    suspend fun getAllQuestionPref(): QuestionPrefResponse {
        return apiService.getPrefQuestion()
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