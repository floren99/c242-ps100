package com.mcaps.mmm.view.welcome

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
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mcaps.mmm.databinding.ActivitySplashBinding
import com.mcaps.mmm.view.MainActivity
import com.mcaps.mmm.view.ViewModelFactory
import com.mcaps.mmm.view.auth.login.LoginActivity
import com.mcaps.mmm.view.auth.login.LoginViewModel

class SplashActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playAnimation()

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }, 3000)
            } else {
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }, 3000)
            }
        }
        setupView()
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

    private fun playAnimation() {
        val logoAlpha = ObjectAnimator.ofFloat(binding.logoSplash, View.ALPHA, 0f, 1f).apply {
            duration = 600
        }
        val lines1 = ObjectAnimator.ofFloat(binding.lines1, View.TRANSLATION_X, -200f, 0f).apply {
            duration = 600
        }
        val lines2 = ObjectAnimator.ofFloat(binding.lines2, View.TRANSLATION_X, -200f, 0f).apply {
            duration = 600
        }
        val lines3 = ObjectAnimator.ofFloat(binding.lines3, View.TRANSLATION_X, 200f, 0f).apply {
            duration = 600
        }
        val lines4 = ObjectAnimator.ofFloat(binding.lines4, View.TRANSLATION_X, 200f, 0f).apply {
            duration = 600
        }
        val leftLine = AnimatorSet().apply {
            playTogether(lines1, lines2)
        }
        val rightLine = AnimatorSet().apply {
            playTogether(lines3, lines4)
        }
        AnimatorSet().apply {
            playSequentially(
                AnimatorSet().apply {
                    playTogether(leftLine, rightLine)
                },
                logoAlpha
            )
            startDelay = 200
            start()
        }
    }
}