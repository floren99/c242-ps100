package com.mcaps.mmm.view.question

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mcaps.mmm.R
import com.mcaps.mmm.databinding.ActivityQuizBinding
import com.mcaps.mmm.view.MainActivity
import com.mcaps.mmm.view.ViewModelFactory
import com.mcaps.mmm.view.dashboard.test.TestViewModel

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private lateinit var quizViewModel: QuizViewModel
    private val sharedViewModel: TestViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory.getInstance(this))[TestViewModel::class.java]
    }
    private var quizType: String = "defaultQuiz"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizType = intent.getStringExtra("QUIZ_ID") ?: "defaultQuiz"
        val factory = ViewModelFactory.getInstance(this)
        quizViewModel = ViewModelProvider(this, factory).get(QuizViewModel::class.java)

        setupToolbar()
        setupObservers()
        setupListeners()

        quizViewModel.fetchQuestions(quizType)
    }

    private fun setupToolbar() {
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
            val quizResult = quizViewModel.calculateResultPercentage()
            when (quizType) {
                "1" -> sharedViewModel.saveQuiz(1, quizResult)
                "2" -> sharedViewModel.saveQuiz(2, quizResult)
                "3" -> sharedViewModel.saveQuiz(3, quizResult)
                "4" -> sharedViewModel.saveQuiz(4, quizResult)
            }
            val intent = Intent(this, MainActivity::class.java).apply {
                Toast.makeText(this@QuizActivity, "Result: $quizResult", Toast.LENGTH_SHORT).show()
                putExtra("RESULT_PERCENTAGE", quizResult)
            }
            startActivity(intent)
            finish()
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
            quizViewModel.saveUserAnswer(selectedAnswer, quizType)
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