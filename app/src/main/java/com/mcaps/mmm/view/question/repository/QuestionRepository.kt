package com.mcaps.mmm.view.question.repository

import com.mcaps.mmm.view.question.model.Question

class QuestionRepository {

    // Daftar soal yang akan ditampilkan
    private val questions = listOf(
        Question(
            questionNumber = 1,
            questionText = "Apa yang paling menarik bagi kamu?",
            optionA = "A. Teknologi dan komputer",
            optionB = "B. Seni dan desain",
            optionC = "C. Bisnis dan manajemen",
            optionD = "D. Kesehatan dan kedokteran",
            correctAnswer = "A"
        ),
        Question(
            questionNumber = 2,
            questionText = "Kamu lebih suka bekerja secara individu atau dalam tim?",
            optionA = "A. Individu",
            optionB = "B. Dalam tim",
            optionC = "C. Tergantung pada situasi",
            optionD = "D. Tidak tahu",
            correctAnswer = "B"
        ),
        Question(
            questionNumber = 3,
            questionText = "Apakah kamu tertarik dengan teknologi dan komputer?",
            optionA = "A. Sangat tertarik",
            optionB = "B. Cukup tertarik",
            optionC = "C. Tidak tertarik",
            optionD = "D. Tidak tahu",
            correctAnswer = "A"
        ),
        Question(
            questionNumber = 4,
            questionText = "Apakah kamu suka bekerja dengan angka dan data?",
            optionA = "A. Sangat suka",
            optionB = "B. Cukup suka",
            optionC = "C. Tidak suka",
            optionD = "D. Tidak tahu",
            correctAnswer = "A"
        ),
        Question(
            questionNumber = 5,
            questionText = "Apakah kamu lebih suka berbicara di depan umum?",
            optionA = "A. Ya, sangat suka",
            optionB = "B. Kadang-kadang",
            optionC = "C. Tidak suka",
            optionD = "D. Tidak pernah",
            correctAnswer = "A"
        ),
        Question(
            questionNumber = 6,
            questionText = "Apakah kamu tertarik dengan seni dan desain visual?",
            optionA = "A. Sangat tertarik",
            optionB = "B. Cukup tertarik",
            optionC = "C. Tidak tertarik",
            optionD = "D. Tidak tahu",
            correctAnswer = "A"
        ),
        Question(
            questionNumber = 7,
            questionText = "Apakah kamu suka riset dan eksperimen?",
            optionA = "A. Sangat suka",
            optionB = "B. Cukup suka",
            optionC = "C. Tidak suka",
            optionD = "D. Tidak tahu",
            correctAnswer = "A"
        ),
        Question(
            questionNumber = 8,
            questionText = "Apakah kamu memiliki minat dalam bidang kesehatan?",
            optionA = "A. Sangat tertarik",
            optionB = "B. Tertarik sedikit",
            optionC = "C. Tidak tertarik",
            optionD = "D. Tidak tahu",
            correctAnswer = "A"
        ),
        Question(
            questionNumber = 9,
            questionText = "Apakah kamu lebih suka merancang solusi daripada hanya mengikuti instruksi?",
            optionA = "A. Ya, lebih suka merancang solusi",
            optionB = "B. Kadang-kadang",
            optionC = "C. Tidak suka",
            optionD = "D. Tidak tahu",
            correctAnswer = "A"
        ),
        Question(
            questionNumber = 10,
            questionText = "Apakah kamu tertarik dengan bidang bisnis dan manajemen?",
            optionA = "A. Sangat tertarik",
            optionB = "B. Cukup tertarik",
            optionC = "C. Tidak tertarik",
            optionD = "D. Tidak tahu",
            correctAnswer = "A"
        )
    )

    // Fungsi untuk mengambil soal berdasarkan nomor soal
    fun getQuestion(number: Int): Question? {
        return questions.find { it.questionNumber == number }
    }

    // Fungsi untuk menyimpan jawaban pengguna
    fun saveUserAnswer(number: Int, answer: String) {
        val question = questions.find { it.questionNumber == number }
        question?.userAnswer = answer
    }

    // Fungsi untuk mengambil daftar soal
    fun getQuestions(): List<Question> {
        return questions
    }
}
