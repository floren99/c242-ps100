package com.mcaps.mmm.view.dashboard.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mcaps.mmm.data.pref.UserModel
import com.mcaps.mmm.data.repository.UserRepository

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getUserSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }
}