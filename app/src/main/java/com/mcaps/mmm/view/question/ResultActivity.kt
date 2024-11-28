package com.mcaps.mmm.view.question

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.mcaps.mmm.view.MainActivity
import com.mcaps.mmm.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val correctAnswersCount = intent.getIntExtra("correct_answers", 0)
        val totalQuestions = intent.getIntExtra("total_questions", 0)

        binding.progressBar.visibility = android.view.View.VISIBLE
        binding.tvCongratulations.visibility = android.view.View.GONE
        binding.tvResult.visibility = android.view.View.GONE
        binding.tvEncouragement.visibility = android.view.View.GONE
        binding.btnFinish.visibility = android.view.View.GONE
        binding.btnGoToHome.visibility = android.view.View.GONE

        Handler(Looper.getMainLooper()).postDelayed({
            binding.progressBar.visibility = android.view.View.GONE
            binding.tvCongratulations.visibility = android.view.View.VISIBLE
            binding.tvResult.visibility = android.view.View.VISIBLE
            binding.tvEncouragement.visibility = android.view.View.VISIBLE
            binding.btnFinish.visibility = android.view.View.VISIBLE
            binding.btnGoToHome.visibility = android.view.View.VISIBLE

            val resultMessage = "You answered $correctAnswersCount out of $totalQuestions questions correctly."
            binding.tvResult.text = resultMessage

            val encouragementMessage = when {
                correctAnswersCount == totalQuestions -> "Perfect score! Amazing work!"
                correctAnswersCount > totalQuestions / 2 -> "Great job! Keep practicing to improve further!"
                else -> "Don’t worry! Practice makes perfect!"
            }
            binding.tvEncouragement.text = encouragementMessage
        }, 2000)

        binding.btnFinish.setOnClickListener {
            finish()
        }

        binding.btnGoToHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}
