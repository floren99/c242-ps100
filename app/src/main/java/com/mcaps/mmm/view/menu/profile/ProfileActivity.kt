package com.mcaps.mmm.view.menu.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mcaps.mmm.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.usernameText.text = intent.getStringExtra("username")
    }
}