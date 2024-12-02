package com.mcaps.mmm.data.repository

import com.mcaps.mmm.data.api.response.LoginResponse
import com.mcaps.mmm.data.api.response.RegisterResponse
import com.mcaps.mmm.data.api.retrofit.ApiService

class ApiUserRepository private constructor(private val apiService: ApiService) {
    suspend fun registerUser(email: String, password: String, name: String): RegisterResponse {
        return apiService.register(email, password, name)
    }

    suspend fun loginUser(email: String, password: String): LoginResponse {
        return apiService.login(email, password)
    }

    companion object {
        @Volatile
        private var instance: ApiUserRepository? = null

        fun getInstance(apiService: ApiService): ApiUserRepository =
            instance ?: synchronized(this) {
                instance ?: ApiUserRepository(apiService)
            }.also { instance = it }
    }
}