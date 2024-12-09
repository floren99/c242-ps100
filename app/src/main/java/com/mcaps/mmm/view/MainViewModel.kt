package com.mcaps.mmm.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mcaps.mmm.data.pref.SettingPreferences
import com.mcaps.mmm.data.pref.UserModel
import com.mcaps.mmm.data.repository.UserRepository
import kotlinx.coroutines.launch

class MainViewModel(private val pref: SettingPreferences) : ViewModel() {
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    fun saveUsername(username: String) {
        _username.value = username
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}