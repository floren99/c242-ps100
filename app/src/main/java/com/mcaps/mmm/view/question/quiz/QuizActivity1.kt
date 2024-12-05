package com.mcaps.mmm.view.question.quiz

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mcaps.mmm.R
import com.mcaps.mmm.databinding.ActivityQuizBinding
import com.mcaps.mmm.view.ViewModelFactory
import com.mcaps.mmm.view.question.QuizViewModel

class QuizActivity1 : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private val quizViewModel: QuizViewModel by viewModels { ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupObservers()
        setupListeners()

        // Fetch quiz questions for quizId = 1
        val quizId = 1
        quizViewModel.fetchQuestions()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Quiz 1"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupObservers() {
        quizViewModel.questions.observe(this) { questions ->
            if (questions.isNotEmpty()) {
                showQuestion() // Display the question when it's available
                updateNavigationButtons() // Ensure the navigation buttons' visibility is updated
            }
        }

        quizViewModel.currentQuestionIndex.observe(this) {
            updateNavigationButtons() // Update button visibility on index change
            showQuestion()
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
            // Notify the fragment about quiz completion and result
            setResult(RESULT_OK)
            finish() // Close the quiz activity and return to the previous screen
        }
    }

    private fun showQuestion() {
        val questions = quizViewModel.questions.value ?: return
        val index = quizViewModel.currentQuestionIndex.value ?: 0
        val question = questions.getOrNull(index) ?: return

        // Set the current question text and options
        binding.tvQuestion.text = "Question ${question.questionNumber}: ${question.questionText}"
        binding.radioButtonA.text = question.optionA
        binding.radioButtonB.text = question.optionB
        binding.radioButtonC.text = question.optionC
        binding.radioButtonD.text = question.optionD
        binding.radioGroup.clearCheck() // Clear previous selection

        // Set the checked radio button based on the user's answer
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
            else -> null
        }
        selectedAnswer?.let {
            quizViewModel.saveUserAnswer(it)
            Log.d("QuizActivity1", "Answer saved: $it")
        }
    }

    private fun updateNavigationButtons() {
        val index = quizViewModel.currentQuestionIndex.value ?: return
        val totalQuestions = quizViewModel.questions.value?.size ?: 0
        Log.d("QuizActivity1", "Current question index: $index")
        Log.d("QuizActivity1", "Total questions: $totalQuestions")

        // Hide the "Next" button and show the "End Test" button on the last question
        if (index == totalQuestions - 1) {
            binding.btnNext.visibility = View.GONE
            binding.btnEndTest.visibility = View.VISIBLE
        } else {
            binding.btnNext.visibility = View.VISIBLE
            binding.btnEndTest.visibility = View.GONE
        }

        // Show or hide the "Back" button based on the current question index
        binding.btnBack.visibility = if (index > 0) View.VISIBLE else View.GONE
    }
}
