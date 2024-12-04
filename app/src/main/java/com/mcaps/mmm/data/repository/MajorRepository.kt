package com.mcaps.mmm.data.repository

import com.mcaps.mmm.data.api.response.MajorResponse
import com.mcaps.mmm.data.api.retrofit.ApiService
import retrofit2.Call

class MajorRepository private constructor(
    private val apiService: ApiService
){
    suspend fun getAllMajor(): Call<MajorResponse> {
        return apiService.getAllMajor()
    }

    companion object {
        @Volatile
        private var instance: MajorRepository? = null

        fun getInstance(apiService: ApiService): MajorRepository =
            instance ?: synchronized(this) {
                instance ?: MajorRepository(apiService)
            }.also { instance = it }
    }
}