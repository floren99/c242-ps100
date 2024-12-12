package com.mcaps.mmm.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mcaps.mmm.R
import com.mcaps.mmm.databinding.ActivityMainBinding
import com.mcaps.mmm.data.pref.SettingPreferences
import com.mcaps.mmm.data.pref.dataStore
import com.mcaps.mmm.view.chatbot.ChatbotActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(MainViewModel::class.java)
        val username = intent.getStringExtra("username")

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        mainViewModel.saveUsername(username ?: "User")

        val viewPager: ViewPager2 = binding.viewPager
        val navView: BottomNavigationView = binding.navView

        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> viewPager.setCurrentItem(0, true)
                R.id.navigation_test -> viewPager.setCurrentItem(1, true)
                R.id.navigation_path -> viewPager.setCurrentItem(2, true)
                R.id.navigation_note -> viewPager.setCurrentItem(3, true)
            }
            true
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                navView.menu.getItem(position).isChecked = true
            }
        })

        binding.chatbot.setOnClickListener {
            val intent = Intent(this, ChatbotActivity::class.java)
            startActivity(intent)
        }
    }
}
