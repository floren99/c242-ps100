package com.mcaps.mmm.view.dashboard.discussion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DiscussionViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is discussion Fragment"
    }
    val text: LiveData<String> = _text
}