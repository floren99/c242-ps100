package com.mcaps.mmm.view.question

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mcaps.mmm.R
import com.mcaps.mmm.databinding.ActivityQuizBinding
import com.mcaps.mmm.view.question.repository.QuestionRepository

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private var currentQuestionIndex = 0
    private var correctAnswersCount = 0
    private lateinit var questionRepository: QuestionRepository
    private var answeredQuestions = mutableListOf<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize QuestionRepository to fetch the questions
        questionRepository = QuestionRepository()

        // Initialize answeredQuestions list based on the number of questions
        answeredQuestions = MutableList(questionRepository.getQuestions().size) { false }

        // Setup Toolbar
        setupToolbar()

        // Display the first question
        showQuestion()

        // Set button listeners
        binding.btnNext.setOnClickListener { handleNextButton() }
        binding.btnBack.setOnClickListener { handleBackButton() }
        binding.btnEndTest.setOnClickListener { showEndTestDialog() }
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = binding.toolbar // Gunakan binding untuk toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Quiz" // Set judul app bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Tambahkan tombol back
    }

    // Display the current question
    private fun showQuestion() {
        val question = questionRepository.getQuestion(currentQuestionIndex + 1)
        if (question != null) {
            binding.tvQuestion.text = "Soal ${question.questionNumber}: ${question.questionText}"
            binding.radioButtonA.text = question.optionA
            binding.radioButtonB.text = question.optionB
            binding.radioButtonC.text = question.optionC
            binding.radioButtonD.text = question.optionD

            // Clear previous selection
            binding.radioGroup.clearCheck()
            when (question.userAnswer) {
                "A" -> binding.radioButtonA.isChecked = true
                "B" -> binding.radioButtonB.isChecked = true
                "C" -> binding.radioButtonC.isChecked = true
                "D" -> binding.radioButtonD.isChecked = true
            }

            // Handle visibility of Next and End Test buttons
            if (currentQuestionIndex == questionRepository.getQuestions().size - 1) {
                binding.btnNext.visibility = View.GONE
                binding.btnEndTest.visibility = View.VISIBLE
            } else {
                binding.btnNext.visibility = View.VISIBLE
                binding.btnEndTest.visibility = View.GONE
            }
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

        // Save user answer using the repository
        if (selectedAnswer.isNotEmpty()) {
            questionRepository.saveUserAnswer(currentQuestionIndex + 1, selectedAnswer)
            answeredQuestions[currentQuestionIndex] = true // Mark as answered
        }
    }

    private fun handleNextButton() {
        saveCurrentAnswer()

        // Check if the answer is correct
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

    private fun showEndTestDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("End Test")
            .setMessage("Are you sure you want to end the test?")
            .setPositiveButton("Yes") { _, _ ->
                navigateToResultActivity()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun navigateToResultActivity() {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("correct_answers", correctAnswersCount)
        intent.putExtra("total_questions", questionRepository.getQuestions().size)
        startActivity(intent)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_quiz, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_show_questions -> {
                // Logika untuk menampilkan daftar soal
                showQuestionListDialog()
                true
            }

            android.R.id.home -> {
                // Handle toolbar back button
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showQuestionListDialog() {
        val questionTitles =
            questionRepository.getQuestions().map { "Soal ${it.questionNumber}" }.toTypedArray()

        // Create a custom adapter for the list
        val adapter = object :
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, questionTitles) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)

                // Change the background color based on whether the question is answered
                if (answeredQuestions[position]) {
                    view.setBackgroundColor(resources.getColor(R.color.colorAnswered)) // Use a color for answered questions
                } else {
                    view.setBackgroundColor(resources.getColor(android.R.color.transparent)) // No background for unanswered questions
                }

                return view
            }
        }

        MaterialAlertDialogBuilder(this)
            .setTitle("Daftar Soal")
            .setAdapter(adapter) { _, which ->
                currentQuestionIndex = which
                showQuestion()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // Override onBackPressed to show confirmation dialog before exiting the quiz
    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        // Show confirmation dialog before exiting the quiz
        MaterialAlertDialogBuilder(this)
            .setTitle("Exit Quiz")
            .setMessage("Are you sure you want to exit the quiz? Your progress will be lost.")
            .setPositiveButton("Yes") { _, _ ->
                super.onBackPressed() // Proceed with exiting the activity
            }
            .setNegativeButton("No", null) // Do nothing if "No" is clicked
            .show()
    }
}
