package com.mcaps.mmm.view.question

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcaps.mmm.data.repository.QuestionPrefRepository
import com.mcaps.mmm.view.question.model.Question
import com.mcaps.mmm.view.question.repository.QuestionRepository
import kotlinx.coroutines.launch

class QuizViewModel(
    private val questionPrefRepository: QuestionPrefRepository,
    private val questionRepository: QuestionRepository
) : ViewModel() {

    private val _questions = MutableLiveData<List<Question>>()
    val questions: LiveData<List<Question>> get() = _questions

    private val _currentQuestionIndex = MutableLiveData(0)
    val currentQuestionIndex: LiveData<Int> get() = _currentQuestionIndex

    private val _correctAnswersCount = MutableLiveData(0)
    val correctAnswersCount: LiveData<Int> get() = _correctAnswersCount

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun fetchQuestions() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                questionRepository.fetchQuestionsFromApi()
                _questions.value = questionRepository.getQuestions()
            } catch (e: Exception) {
                _error.value = "Failed to load questions: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun moveToNextQuestion() {
        _currentQuestionIndex.value = (_currentQuestionIndex.value ?: 0) + 1
    }

    fun moveToPreviousQuestion() {
        _currentQuestionIndex.value = (_currentQuestionIndex.value ?: 0).coerceAtLeast(1) - 1
    }

    fun saveUserAnswer(userAnswer: String) {
        val index = _currentQuestionIndex.value ?: return
        _questions.value?.let { list ->
            val updatedQuestions = list.toMutableList()
            val question = updatedQuestions[index]
            updatedQuestions[index] = question.copy(userAnswer = userAnswer)
            _questions.value = updatedQuestions

            if (question.correctAnswer == userAnswer) {
                _correctAnswersCount.value = (_correctAnswersCount.value ?: 0) + 1
            }
        }
    }
}
