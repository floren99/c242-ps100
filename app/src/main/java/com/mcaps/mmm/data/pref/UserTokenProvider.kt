package com.mcaps.mmm.data.pref

import com.mcaps.mmm.data.api.retrofit.TokenProvider
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

class UserTokenProvider(private val userPreference: UserPreference) : TokenProvider {
    override fun getToken(): String? {
        return runBlocking {
            userPreference.getSession().firstOrNull()?.token
        }
    }
}