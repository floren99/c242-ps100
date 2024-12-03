package com.mcaps.mmm.view.question

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mcaps.mmm.databinding.ActivityQuestionBinding
import com.mcaps.mmm.view.auth.login.LoginActivity  // Import the LoginActivity

class QuestionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using ViewBinding
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Button "Mulai Tes" action
        binding.btnStartTest.setOnClickListener {
            // Replace with RulesFragment
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(android.R.id.content, RulesFragment())  // Replace with the RulesFragment
            transaction.addToBackStack(null)  // To allow back navigation
            transaction.commit()
        }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
