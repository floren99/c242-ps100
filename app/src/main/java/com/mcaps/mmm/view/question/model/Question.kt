package com.mcaps.mmm.view.question.model

data class Question(
    val questionNumber: Int,  // Nomor soal
    val questionText: String,  // Teks soal
    val optionA: String,      // Pilihan A
    val optionB: String,      // Pilihan B
    val optionC: String,      // Pilihan C
    val optionD: String,      // Pilihan D
    val correctAnswer: String, // Jawaban yang benar (A, B, C, D)
    var userAnswer: String = "" // Jawaban pengguna, default kosong
)
