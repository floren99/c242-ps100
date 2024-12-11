package com.mcaps.mmm.view.menu.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcaps.mmm.data.pref.UserModel
import com.mcaps.mmm.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _user = MutableStateFlow<UserModel?>(null)
    val user: StateFlow<UserModel?> get() = _user

    fun loadUser() {
        viewModelScope.launch {
            userRepository.getSession().collect {
                _user.value = it
            }
        }
    }

    fun updateUser(username: String, profileImageUri: String?) {
        viewModelScope.launch {
            userRepository.getSession().collect { currentUser ->
                val updatedUser = currentUser.copy(
                    username = username,
                    profileImageUri = profileImageUri
                )
                userRepository.saveSession(updatedUser)
                _user.value = updatedUser
            }
        }
    }
}