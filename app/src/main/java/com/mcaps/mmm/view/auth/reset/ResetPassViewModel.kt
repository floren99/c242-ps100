package com.mcaps.mmm.view.auth.reset

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcaps.mmm.data.api.response.ResetPassResponse
import com.mcaps.mmm.data.repository.ApiUserRepository
import kotlinx.coroutines.launch

class ResetPassViewModel(private val apiUserRepository: ApiUserRepository) : ViewModel() {

    private val _resetResponse = MutableLiveData<ResetPassResponse>()
    val resetResponse: LiveData<ResetPassResponse> = _resetResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun resetPass(email: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = apiUserRepository.resetPass(email)
                _resetResponse.value = response
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "An error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }
}