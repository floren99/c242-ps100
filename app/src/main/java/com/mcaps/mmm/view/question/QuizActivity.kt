package com.mcaps.mmm.view.question

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mcaps.mmm.R
import com.mcaps.mmm.data.api.retrofit.ApiConfig
import com.mcaps.mmm.data.repository.QuestionPrefRepository
import com.mcaps.mmm.databinding.ActivityQuizBinding
import com.mcaps.mmm.view.question.repository.QuestionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private lateinit var questionRepository: QuestionRepository
    private val apiService = ApiConfig.getApiService()
    private val questionPrefRepository = QuestionPrefRepository.getInstance(apiService)
    private var currentQuestionIndex = 0
    private var correctAnswersCount = 0
    private var answeredQuestions = mutableListOf<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        questionRepository = QuestionRepository(questionPrefRepository)
        fetchQuestions()
        setupToolbar()
        setupListeners()
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Quiz"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupListeners() {
        binding.btnNext.setOnClickListener { handleNextButton() }
        binding.btnBack.setOnClickListener { handleBackButton() }
        binding.btnEndTest.setOnClickListener { }
    }

    private fun fetchQuestions() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                binding.progressBarQuizPref.visibility = View.VISIBLE
                questionRepository.fetchQuestionsFromApi()
                withContext(Dispatchers.Main) {
                    answeredQuestions = MutableList(questionRepository.getQuestions().size) { false }
                    showQuestion()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle error, e.g., show a retry button or a toast message
                Toast.makeText(this@QuizActivity, "Failed to load questions", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBarQuizPref.visibility = View.GONE
            }
        }
    }

    private fun showQuestion() {
        val question = questionRepository.getQuestion(currentQuestionIndex + 1)
        if (question != null) {
            binding.tvQuestion.text = "Question ${question.questionNumber}: ${question.questionText}"
            binding.radioButtonA.text = question.optionA
            binding.radioButtonB.text = question.optionB
            binding.radioButtonC.text = question.optionC
            binding.radioButtonD.text = question.optionD
            binding.radioGroup.clearCheck()
            when (question.userAnswer) {
                "A" -> binding.radioButtonA.isChecked = true
                "B" -> binding.radioButtonB.isChecked = true
                "C" -> binding.radioButtonC.isChecked = true
                "D" -> binding.radioButtonD.isChecked = true
            }
            updateNavigationButtons()
        }
    }

    private fun updateNavigationButtons() {
        if (currentQuestionIndex == questionRepository.getQuestions().size - 1) {
            binding.btnNext.visibility = View.GONE
            binding.btnEndTest.visibility = View.VISIBLE
        } else {
            binding.btnNext.visibility = View.VISIBLE
            binding.btnEndTest.visibility = View.GONE
        }
    }

    private fun saveCurrentAnswer() {
        val selectedAnswer = when (binding.radioGroup.checkedRadioButtonId) {
            R.id.radioButtonA -> "A"
            R.id.radioButtonB -> "B"
            R.id.radioButtonC -> "C"
            R.id.radioButtonD -> "D"
            else -> ""
        }

        if (selectedAnswer.isNotEmpty()) {
            questionRepository.saveUserAnswer(currentQuestionIndex + 1, selectedAnswer)
            answeredQuestions[currentQuestionIndex] = true
        }
    }

    private fun handleNextButton() {
        saveCurrentAnswer()
        checkAnswer()
        if (currentQuestionIndex < questionRepository.getQuestions().size - 1) {
            currentQuestionIndex++
            showQuestion()
        }
    }

    private fun handleBackButton() {
        saveCurrentAnswer()
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--
            showQuestion()
        }
    }

    private fun checkAnswer() {
        val question = questionRepository.getQuestion(currentQuestionIndex + 1)
        val selectedAnswer = when (binding.radioGroup.checkedRadioButtonId) {
            R.id.radioButtonA -> "A"
            R.id.radioButtonB -> "B"
            R.id.radioButtonC -> "C"
            R.id.radioButtonD -> "D"
            else -> ""
        }

        if (question != null && selectedAnswer == question.correctAnswer) {
            correctAnswersCount++
        }
    }
}
