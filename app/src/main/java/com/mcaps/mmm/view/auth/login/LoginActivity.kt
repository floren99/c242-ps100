package com.mcaps.mmm.view.auth.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.mcaps.mmm.databinding.ActivityLoginBinding
import com.mcaps.mmm.view.MainActivity
import com.mcaps.mmm.view.ViewModelFactory
import com.mcaps.mmm.view.auth.register.RegisterFragment
import com.mcaps.mmm.data.pref.UserModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupAction()
        playAnimation()

        binding.tvSignup.setOnClickListener{
            val dialogFragment = RegisterFragment()
            dialogFragment.show(supportFragmentManager, "register")
        }

        viewModel.navigateToMainActivity.observe(this) { navigate ->
            if (navigate) {
                navigate()
            }
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            if (validateInput(email, password)) {
                viewModel.loginUser(email, password)

                viewModel.isLoading.observe(this) { isLoading ->
                    binding.progressBarLogin.visibility = if (isLoading) View.VISIBLE else View.GONE
                }

                viewModel.loginResponse.observe(this) { response ->
                    val token = response.loginResult?.token
                    val username = response.loginResult?.username ?: "User"
                    if (token != null) {
                        val user = UserModel(email, token, true, username)
                        viewModel.saveSession(user)
                    } else {
                        AlertDialog.Builder(this)
                            .setTitle("Error")
                            .setMessage("Login failed, token is missing.")
                            .setPositiveButton("OK", null)
                            .show()
                    }
                }

                viewModel.errorMessage.observe(this) { errorMessage ->
                    errorMessage?.let {
                        AlertDialog.Builder(this)
                            .setTitle("Error")
                            .setMessage(it)
                            .setPositiveButton("OK", null)
                            .show()
                    }
                }
            }
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        return when {
            email.isBlank() -> {
                showErrorDialog("Email cannot be empty")
                false
            }
            password.isEmpty() -> {
                showErrorDialog("Password cannot be empty")
                false
            }
            password.length < 8 -> {
                showErrorDialog("Password must be at least 8 characters")
                false
            }
            else -> true
        }
    }

    private fun navigate() {
        Toast.makeText(this@LoginActivity, "Logged in successfully", Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }, 1000)
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Error")
            setMessage(message)
            setPositiveButton("OK") { _, _ -> }
            create()
            show()
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val tvEmail = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(300)
        val ivEmail = ObjectAnimator.ofFloat(binding.edLoginEmail, View.ALPHA, 1f).setDuration(300)
        val lEmail = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val tvPass = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(300)
        val ivPass = ObjectAnimator.ofFloat(binding.edLoginPassword, View.ALPHA, 1f).setDuration(300)
        val lPass = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(300)
        val signup = ObjectAnimator.ofFloat(binding.tvSignup, View.ALPHA, 1f).setDuration(300)
        val tvDontHaveAccount = ObjectAnimator.ofFloat(binding.tvDontHaveAccount, View.ALPHA, 1f).setDuration(300)

        val email = AnimatorSet().apply {
            playTogether(tvEmail, ivEmail, lEmail)
        }
        val pass = AnimatorSet().apply {
            playTogether(tvPass, ivPass, lPass)
        }
        val reg = AnimatorSet().apply {
            playTogether(tvDontHaveAccount, signup)
        }

        AnimatorSet().apply {
            playSequentially(email, pass, login, reg)
            startDelay = 200
            start()
        }
    }
}
