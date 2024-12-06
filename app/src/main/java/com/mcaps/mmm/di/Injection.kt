package com.mcaps.mmm.di

import android.content.Context
import com.mcaps.mmm.data.api.retrofit.ApiConfig
import com.mcaps.mmm.data.local.AppDatabase
import com.mcaps.mmm.data.local.dao.QuizResultDao
import com.mcaps.mmm.data.pref.UserPreference
import com.mcaps.mmm.data.pref.UserTokenProvider
import com.mcaps.mmm.data.pref.dataStore
import com.mcaps.mmm.data.repository.ApiUserRepository
import com.mcaps.mmm.data.repository.MajorRepository
import com.mcaps.mmm.data.repository.PredictRepository
import com.mcaps.mmm.data.repository.QuestionPrefRepository
import com.mcaps.mmm.data.repository.UserRepository

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
    fun provideApiUserRepository(): ApiUserRepository {
        val apiService = ApiConfig.getApiService()
        return ApiUserRepository.getInstance(apiService)
    }
    fun providePredictRepository(): PredictRepository {
        val apiService = ApiConfig.getApiService(null, "ml")
        return PredictRepository.getInstance(apiService)
    }
    fun provideMajorRepository(): MajorRepository {
        val apiService = ApiConfig.getApiService()
        return MajorRepository.getInstance(apiService)
    }
    fun provideQuestionPrefRepository(): QuestionPrefRepository {
        val apiService = ApiConfig.getApiService()
        return QuestionPrefRepository.getInstance(apiService)
    }
    fun provideQuizResultDao(context: Context): QuizResultDao {
        val database = AppDatabase.getInstance(context)
        return database.quizResultDao()
    }
}