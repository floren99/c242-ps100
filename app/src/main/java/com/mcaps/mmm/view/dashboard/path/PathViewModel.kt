package com.mcaps.mmm.view.dashboard.path

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PathViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is path Fragment"
    }
    val text: LiveData<String> = _text
}