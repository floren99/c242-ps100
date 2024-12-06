package com.mcaps.mmm.view.dashboard.test

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mcaps.mmm.data.api.response.PredictResponse
import com.mcaps.mmm.data.pref.PredictRequest
import com.mcaps.mmm.data.repository.MajorRepository
import com.mcaps.mmm.data.repository.PredictRepository

class TestViewModel (private val predictRepository: PredictRepository): ViewModel() {

    private val _scores = MutableLiveData<List<Int>>()
    val scores: MutableLiveData<List<Int>> get() = _scores

    private val _minat = MutableLiveData<List<Int>>()
    val minat: MutableLiveData<List<Int>> get() = _minat

    fun updateScores(newScores: List<Int>) {
        _scores.value = newScores
    }
    fun updateMinat(newMinat: List<Int>)
    {
        Log.d("TestViewModel", "Updating minat with: ${newMinat.joinToString(", ")}")
        _minat.value = newMinat
    }

    var answers: List<Int> = emptyList()

    suspend fun predict(input : PredictRequest): PredictResponse {
        return predictRepository.predict(input)
    }

}