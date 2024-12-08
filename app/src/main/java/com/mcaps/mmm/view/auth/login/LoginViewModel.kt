package com.mcaps.mmm.view.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.mcaps.mmm.data.api.response.LoginResponse
import com.mcaps.mmm.data.pref.UserModel
import com.mcaps.mmm.data.repository.ApiUserRepository
import com.mcaps.mmm.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val apiRepository: ApiUserRepository, private val repository: UserRepository) : ViewModel() {
    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isSessionSaved = MutableLiveData<Boolean>()
    val isSessionSaved: LiveData<Boolean> get() = _isSessionSaved

    private val _navigateToMainActivity = MutableLiveData<Boolean>()
    val navigateToMainActivity: LiveData<Boolean> get() = _navigateToMainActivity

    fun loginUser(email: String, password: String) {
        _isLoading.value = true
        _errorMessage.value = null
        viewModelScope.launch {
            try {
                val response = apiRepository.loginUser(email, password)
                _loginResponse.value = response
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
    fun isUserLoggedIn(): LiveData<Boolean> {
        return repository.getSession().asLiveData().map { user ->
            user.token.isNotEmpty()
        }
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
            _isSessionSaved.value = true
            _navigateToMainActivity.value = true
        }
    }
}