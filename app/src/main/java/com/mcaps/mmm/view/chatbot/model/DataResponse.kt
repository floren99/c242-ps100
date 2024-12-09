package com.mcaps.mmm.view.chatbot.model

data class DataResponse(
    val isUser: Int,
    val prompt: String,
    val imageUri: String,
    val isLoading: Boolean = false // Menambahkan properti untuk loading
)
