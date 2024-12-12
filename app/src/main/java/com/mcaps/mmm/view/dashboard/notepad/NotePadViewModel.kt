package com.mcaps.mmm.view.dashboard.notepad

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mcaps.mmm.data.repository.UserRepository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NotePadViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _topNote = MutableLiveData<String>()
    val topNote: LiveData<String> = _topNote

    private val _bottomNote = MutableLiveData<String>()
    val bottomNote: LiveData<String> = _bottomNote

    fun loadNotepadData() {
        viewModelScope.launch {
            userRepository.getNotepadData().collect { notes ->
                _topNote.postValue(notes.first)
                _bottomNote.postValue(notes.second)
            }
        }
    }
    fun saveNotepadData(topNote: String, bottomNote: String) {
        viewModelScope.launch {
            userRepository.saveNotepadData(topNote, bottomNote)
        }
    }
    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}
