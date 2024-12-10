package com.mcaps.mmm.view.menu

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.mcaps.mmm.R
import com.mcaps.mmm.data.pref.SettingPreferences
import com.mcaps.mmm.data.pref.themeDataStore
import com.mcaps.mmm.databinding.ActivityMainBinding
import com.mcaps.mmm.databinding.ActivityMenuBinding
import com.mcaps.mmm.view.MainViewModel
import com.mcaps.mmm.view.ViewModelFactory
import com.mcaps.mmm.view.auth.login.LoginActivity
import com.mcaps.mmm.view.auth.login.LoginViewModel
import com.mcaps.mmm.view.chatbot.ChatbotActivity
import com.mcaps.mmm.view.menu.profile.ProfileActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var loginViewModel: LoginViewModel

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(this.themeDataStore)
        mainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(MainViewModel::class.java)

        val email = intent.getStringExtra("email")
        val username = intent.getStringExtra("username")

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            binding.themeSwitch.isChecked = isDarkModeActive
        }

        loginViewModel = ViewModelProvider(this, ViewModelFactory.getInstance(this)).get(LoginViewModel::class.java)

        binding.username.text = username
        binding.email.text = email

        binding.btnSignoutMenu.setOnClickListener {
            loginViewModel.logout()
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        binding.myProfile.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }

        binding.aboutUs.setOnClickListener{
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        binding.themeSwitch.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            GlobalScope.launch(Dispatchers.IO) {
                mainViewModel.saveThemeSetting(isChecked)

                delay(500)

                withContext(Dispatchers.Main) {
                    if (isChecked) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                }
            }
        }
    }
}