package com.mcaps.mmm.data.repository

import com.mcaps.mmm.data.api.response.PredictResponse
import com.mcaps.mmm.data.api.retrofit.ApiService
import com.mcaps.mmm.data.pref.PredictRequest

class PredictRepository private constructor(
    private val apiService: ApiService,
){
    suspend fun predict(input: PredictRequest): PredictResponse {
        return apiService.predict(input)
    }

    companion object {
        @Volatile
        private var instance: PredictRepository? = null

        fun getInstance(apiService: ApiService): PredictRepository =
            instance ?: synchronized(this) {
                instance ?: PredictRepository(apiService)
            }.also { instance = it }
    }
}