package com.mcaps.mmm.view.dashboard.path

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mcaps.mmm.data.api.response.MajorDataItem
import com.mcaps.mmm.data.api.response.MajorResponse
import com.mcaps.mmm.data.repository.MajorRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PathViewModel (private val majorRepository: MajorRepository): ViewModel() {

    private val _pathList = MutableLiveData<List<MajorDataItem>>()
    val pathList: LiveData<List<MajorDataItem>> = _pathList

    private val _major = MutableLiveData<MajorDataItem>()
    val major: LiveData<MajorDataItem> = _major

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    suspend fun getPaths() {
        _isLoading.value = true
        majorRepository.getAllMajor().enqueue(object : Callback<MajorResponse> {
            override fun onResponse(call: Call<MajorResponse>, response: Response<MajorResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _pathList.value = response.body()?.data
                } else {
                    _errorMessage.value = "Failed to load items"
                }
            }

            override fun onFailure(call: Call<MajorResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = t.message
            }
        })
    }
}