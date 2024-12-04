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
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mcaps.mmm.R
import com.mcaps.mmm.data.api.retrofit.ApiConfig
import com.mcaps.mmm.data.repository.QuestionPrefRepository
import com.mcaps.mmm.databinding.ActivityQuizBinding
import com.mcaps.mmm.view.ViewModelFactory
import com.mcaps.mmm.view.question.repository.QuestionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private lateinit var quizViewModel: QuizViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        val factory = ViewModelFactory.getInstance(this)
        quizViewModel = ViewModelProvider(this, factory).get(QuizViewModel::class.java)

        setupToolbar()
        setupObservers()
        setupListeners()

        // Fetch questions
        quizViewModel.fetchQuestions()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Quiz"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupObservers() {
        quizViewModel.questions.observe(this) { questions ->
            if (questions.isNotEmpty()) showQuestion()
        }

        quizViewModel.currentQuestionIndex.observe(this) {
            updateNavigationButtons()
            showQuestion()
        }

        quizViewModel.correctAnswersCount.observe(this) { count ->
            // Handle correct answers count, if needed
        }

        quizViewModel.loading.observe(this) { isLoading ->
            binding.progressBarQuizPref.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        quizViewModel.error.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupListeners() {
        binding.btnNext.setOnClickListener {
            saveCurrentAnswer()
            quizViewModel.moveToNextQuestion()
        }
        binding.btnBack.setOnClickListener {
            saveCurrentAnswer()
            quizViewModel.moveToPreviousQuestion()
        }
        binding.btnEndTest.setOnClickListener {
            saveCurrentAnswer()
            // Navigate to results or perform end test logic
        }
    }

    private fun showQuestion() {
        val questions = quizViewModel.questions.value ?: return
        val index = quizViewModel.currentQuestionIndex.value ?: 0
        val question = questions.getOrNull(index) ?: return

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
            quizViewModel.saveUserAnswer(selectedAnswer)
        }
    }

    private fun updateNavigationButtons() {
        val index = quizViewModel.currentQuestionIndex.value ?: 0
        val totalQuestions = quizViewModel.questions.value?.size ?: 0

        binding.btnBack.visibility = if (index > 0) View.VISIBLE else View.GONE
        if (index == totalQuestions - 1) {
            binding.btnNext.visibility = View.GONE
            binding.btnEndTest.visibility = View.VISIBLE
        } else {
            binding.btnNext.visibility = View.VISIBLE
            binding.btnEndTest.visibility = View.GONE
        }
    }
}