package com.mcaps.mmm.view.question.repository

import com.mcaps.mmm.data.repository.QuestionPrefRepository
import com.mcaps.mmm.view.question.model.Question
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuestionRepository(private val questionPrefRepository: QuestionPrefRepository) {

    private val questions = mutableListOf<Question>()

    suspend fun fetchQuestionsFromApi(quizType : String) {
        val response = when (quizType) {
            "1" -> questionPrefRepository.getQuestionPref1()
            "2" -> questionPrefRepository.getQuestionPref2()
            "3" -> questionPrefRepository.getQuestionPref3()
            "4" -> questionPrefRepository.getQuestionPref4()
            else -> questionPrefRepository.getAllQuestionPref()
        }
        questions.clear()
        questions.addAll(response.questions.mapIndexed { index, item ->
            Question(
                questionNumber = index + 1,
                questionText = item.questionText.orEmpty(),
                optionA = item.options.getOrNull(0) ?: "Option A",
                optionB = item.options.getOrNull(1) ?: "Option B",
                optionC = item.options.getOrNull(2) ?: "Option C",
                optionD = item.options.getOrNull(3) ?: "Option D",
                correctAnswer = item.correctAnswer.orEmpty()
            )
        })
    }

    fun getQuestions(): List<Question> = questions

    fun getQuestion(questionNumber: Int): Question? =
        questions.firstOrNull { it.questionNumber == questionNumber }

    fun saveUserAnswer(questionNumber: Int, answer: String) {
        questions.firstOrNull { it.questionNumber == questionNumber }?.userAnswer = answer
    }
}
