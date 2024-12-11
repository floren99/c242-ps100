package com.mcaps.mmm.view.dashboard.discussion

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mcaps.mmm.databinding.FragmentDiscussionBinding

class DiscussionFragment : Fragment() {
    private var _binding: FragmentDiscussionBinding? = null
    private val binding get() = _binding!!

    private val sharedPreferences by lazy {
        requireActivity().getSharedPreferences("notepad_preferences", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiscussionBinding.inflate(inflater, container, false)

        val savedTopNote = sharedPreferences.getString("note_top", "")
        val savedBottomNote = sharedPreferences.getString("note_bottom", "")

        binding.editTextNotepadTop.setText(savedTopNote)
        binding.editTextNotepadBottom.setText(savedBottomNote)

        binding.editTextNotepadTop.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                sharedPreferences.edit().putString("note_top", s.toString()).apply()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.editTextNotepadBottom.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                sharedPreferences.edit().putString("note_bottom", s.toString()).apply()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
