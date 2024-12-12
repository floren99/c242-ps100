package com.mcaps.mmm.view.dashboard.notepad

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mcaps.mmm.databinding.FragmentNotepadBinding
import com.mcaps.mmm.view.ViewModelFactory
import com.mcaps.mmm.data.repository.UserRepository
import com.mcaps.mmm.data.pref.UserPreference
import com.mcaps.mmm.data.pref.dataStore

class NotePadFragment : Fragment() {
    private var _binding: FragmentNotepadBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NotePadViewModel
    private lateinit var userRepository: UserRepository

    private var lastTextChangedTime: Long = 0
    private val DEBOUNCE_TIME = 300L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotepadBinding.inflate(inflater, container, false)

        val userPreference = UserPreference.getInstance(requireContext().dataStore)
        userRepository = UserRepository.getInstance(userPreference)

        val factory = ViewModelFactory(userRepository = userRepository) // Passing UserRepository correctly
        viewModel = ViewModelProvider(this, factory).get(NotePadViewModel::class.java)

        viewModel.topNote.observe(viewLifecycleOwner, { topNote ->
            if (binding.editTextNotepadTop.text.toString() != topNote) {
                binding.editTextNotepadTop.setText(topNote)
            }
        })

        viewModel.bottomNote.observe(viewLifecycleOwner, { bottomNote ->
            if (binding.editTextNotepadBottom.text.toString() != bottomNote) {
                binding.editTextNotepadBottom.setText(bottomNote)
            }
        })
        viewModel.loadNotepadData()

        addTextChangedListenerWithDebounce(binding.editTextNotepadTop)
        addTextChangedListenerWithDebounce(binding.editTextNotepadBottom)

        return binding.root
    }

    private fun addTextChangedListenerWithDebounce(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastTextChangedTime > DEBOUNCE_TIME) {
                    lastTextChangedTime = currentTime
                    // Save the notepad data asynchronously
                    s?.let {
                        viewModel.saveNotepadData(
                            binding.editTextNotepadTop.text.toString(),
                            binding.editTextNotepadBottom.text.toString()
                        )
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
