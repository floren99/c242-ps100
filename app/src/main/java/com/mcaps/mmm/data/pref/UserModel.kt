package com.mcaps.mmm.data.pref

data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false,
    val username : String,
    val profileImageUri: String? = null
)
