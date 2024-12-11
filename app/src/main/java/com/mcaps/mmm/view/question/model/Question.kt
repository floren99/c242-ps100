package com.mcaps.mmm.view.question.model

data class Question(
    val questionNumber: Int,
    val questionText: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctAnswer: String,
    var userAnswer: String = ""
)
