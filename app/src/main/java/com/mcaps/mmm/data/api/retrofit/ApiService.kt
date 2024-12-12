package com.mcaps.mmm.data.api.retrofit

import com.mcaps.mmm.data.api.response.LoginResponse
import com.mcaps.mmm.data.api.response.MajorResponse
import com.mcaps.mmm.data.api.response.PredictResponse
import com.mcaps.mmm.data.api.response.QuestionPrefResponse
import com.mcaps.mmm.data.api.response.RegisterResponse
import com.mcaps.mmm.data.api.response.ResetPassResponse
import com.mcaps.mmm.data.pref.PredictRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("username") name: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("reset-password")
    suspend fun resetPass(
        @Field("email") email: String
    ): ResetPassResponse

    @GET("questions")
    suspend fun getPrefQuestion(): QuestionPrefResponse

    @GET("questions-1")
    suspend fun getPrefQuestion1(): QuestionPrefResponse

    @GET("questions-2")
    suspend fun getPrefQuestion2(): QuestionPrefResponse

    @GET("questions-3")
    suspend fun getPrefQuestion3(): QuestionPrefResponse

    @GET("questions-4")
    suspend fun getPrefQuestion4(): QuestionPrefResponse

    @GET("major")
    fun getAllMajor(): Call<MajorResponse>

    @GET("major/{id}")
    fun getMajorById(@Path("id") id: Int): Call<MajorResponse>

    @POST("predict")
    suspend fun predict(
        @Body request: PredictRequest
    ): PredictResponse
}