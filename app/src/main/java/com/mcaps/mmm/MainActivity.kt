package com.mcaps.mmm

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mcaps.mmm.databinding.ActivityMainBinding
import com.mcaps.mmm.ui.ViewPagerAdapter
import com.mcaps.mmm.ui.menu.SettingPreferences
import com.mcaps.mmm.ui.menu.dataStore
import com.mcaps.mmm.ui.question.QuestionActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Theme management
        val pref = SettingPreferences.getInstance(application.dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(MainViewModel::class.java)

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // Setup ViewPager2
        val viewPager: ViewPager2 = binding.viewPager
        val navView: BottomNavigationView = binding.navView

        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> viewPager.setCurrentItem(0, true)
                R.id.navigation_dashboard -> viewPager.setCurrentItem(1, true)
                R.id.navigation_notifications -> viewPager.setCurrentItem(2, true)
                R.id.navigation_menu -> viewPager.setCurrentItem(3, true)
            }
            true
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                navView.menu.getItem(position).isChecked = true
            }
        })

        // FAB click with animation
        binding.question.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        }

        // Adding a click animation (scale effect)
        val fabAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        binding.question.setOnClickListener {
            binding.question.startAnimation(fabAnimation)
            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        }
    }
}
