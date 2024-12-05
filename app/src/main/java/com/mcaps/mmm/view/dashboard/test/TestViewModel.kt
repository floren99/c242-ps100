package com.mcaps.mmm.view.dashboard.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TestViewModel : ViewModel() {

    private val _scores = MutableLiveData<Map<String, Int>>()
    val scores: LiveData<Map<String, Int>> get() = _scores

    private val _minat = MutableLiveData<List<Int>>()
    val minat: MutableLiveData<List<Int>> get() = _minat

    fun updateScores(newScores: Map<String, Int>) {
        _scores.value = newScores
    }
    fun updateMinat(newMinat: List<Int>) {
        _minat.value = newMinat
    }
    var answers: List<Int> = emptyList()
}