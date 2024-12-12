package com.mcaps.mmm.data.api.retrofit

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.mcaps.mmm.BuildConfig

object ApiConfig {
    fun getApiService(tokenProvider: TokenProvider? = null, apiChoice: String? = null): ApiService {
        val apiUrl = if (apiChoice == "ml") {
            BuildConfig.ML_API_URL
        } else {
            BuildConfig.API_BASE_URL
        }
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val token = tokenProvider?.getToken()
                val requestBuilder = chain.request().newBuilder()

                if (!token.isNullOrEmpty()) {
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }

                Log.d("ApiConfig", "Using token: $token")
                chain.proceed(requestBuilder.build())
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}
interface TokenProvider {
    fun getToken(): String?
}
