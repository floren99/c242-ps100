package com.mcaps.mmm.view.dashboard.test

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcaps.mmm.data.api.response.PredictResponse
import com.mcaps.mmm.data.pref.PredictRequest
import com.mcaps.mmm.data.pref.TestPreference
import com.mcaps.mmm.data.repository.MajorRepository
import com.mcaps.mmm.data.repository.PredictRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TestViewModel (private val predictRepository: PredictRepository, private val testPreference: TestPreference): ViewModel() {

    private val _scores = MutableLiveData<List<Int>>().apply { value = emptyList() }
    val scores: LiveData<List<Int>> get() = _scores

    private val _minat = MutableLiveData<List<Int>>().apply { value = emptyList() }
    val minat: LiveData<List<Int>> get() = _minat

    private val _quiz1 = MutableLiveData<Int>().apply { value = 0 }
    val quiz1: LiveData<Int> get() = _quiz1

    private val _quiz2 = MutableLiveData<Int>().apply { value = 0 }
    val quiz2: LiveData<Int> get() = _quiz2

    private val _quiz3 = MutableLiveData<Int>().apply { value = 0 }
    val quiz3: LiveData<Int> get() = _quiz3

    private val _quiz4 = MutableLiveData<Int>().apply { value = 0 }
    val quiz4: LiveData<Int> get() = _quiz4

    init {
        viewModelScope.launch {
            testPreference.getScores().collect { scores ->
                _scores.value = scores
            }
        }

        viewModelScope.launch {
            testPreference.getMinat().collect { minat ->
                _minat.value = minat
            }
        }
        viewModelScope.launch {
            testPreference.getQuiz(TestPreference.QUIZ1_KEY).collect { quiz1Score ->
                _quiz1.value = quiz1Score
            }
        }
        viewModelScope.launch {
            testPreference.getQuiz(TestPreference.QUIZ2_KEY).collect { quiz2Score ->
                _quiz2.value = quiz2Score
            }
        }
        viewModelScope.launch {
            testPreference.getQuiz(TestPreference.QUIZ3_KEY).collect { quiz3Score ->
                _quiz3.value = quiz3Score
            }
        }
        viewModelScope.launch {
            testPreference.getQuiz(TestPreference.QUIZ4_KEY).collect { quiz4Score ->
                _quiz4.value = quiz4Score
            }
        }
    }

    fun updateScores(newScores: List<Int>) {
        _scores.value = newScores
        viewModelScope.launch {
            testPreference.saveScores(newScores)
        }
        Log.d("TestViewModel", "Scores updated: $newScores")
    }
    fun updateMinat(newMinat: List<Int>) {
        _minat.value = newMinat
        viewModelScope.launch {
            testPreference.saveMinat(newMinat)
        }
        Log.d("TestViewModel", "Minat updated: $newMinat")
    }

    fun saveQuiz(quizIndex: Int, value: Int) {
        val quizKey = when (quizIndex) {
            1 -> TestPreference.QUIZ1_KEY
            2 -> TestPreference.QUIZ2_KEY
            3 -> TestPreference.QUIZ3_KEY
            4 -> TestPreference.QUIZ4_KEY
            else -> throw IllegalArgumentException("Invalid quiz index")
        }

        viewModelScope.launch {
            testPreference.saveQuiz(quizKey, value)
        }
    }

    var answers: List<Int> = emptyList()

    fun getFinalInput(): List<Int> {
        val scoresList = _scores.value ?: emptyList()
        val quizList = listOf(_quiz1.value, _quiz2.value, _quiz3.value, _quiz4.value).mapNotNull { it }
        val minatList = _minat.value ?: emptyList()

        Log.d("TestViewModel", "Scores: $scoresList")
        Log.d("TestViewModel", "Quizzes: $quizList")
        Log.d("TestViewModel", "Minat: $minatList")

        answers = scoresList + quizList + minatList
        Log.d("TestViewModel", "Final input: $answers")

        return answers
    }

    suspend fun predict(input : PredictRequest): PredictResponse {
        return predictRepository.predict(input)
    }

}