package com.mcaps.mmm.view.dashboard.test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mcaps.mmm.R
import com.mcaps.mmm.data.pref.CekModel
import com.mcaps.mmm.databinding.FragmentRegisterBinding
import com.mcaps.mmm.databinding.FragmentScoreBinding
import com.mcaps.mmm.view.ViewModelFactory

class ScoreFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentScoreBinding? = null
    private val binding get() = checkNotNull(_binding) {  }
    private var cekModel: CekModel? = null
    private val sharedViewModel: TestViewModel by lazy{
        ViewModelProvider(this, ViewModelFactory.getInstance(requireContext()))[TestViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitButton.setOnClickListener {
            val mathScore = binding.editScoreMath.text.toString().toIntOrNull() ?: 0
            val bioScore = binding.editScoreBio.text.toString().toIntOrNull() ?: 0
            val physScore = binding.editScorePhysics.text.toString().toIntOrNull() ?: 0
            val chemScore = binding.editScoreChemistry.text.toString().toIntOrNull() ?: 0
            val engScore = binding.editScoreEnglish.text.toString().toIntOrNull() ?: 0

            if (mathScore !in 0..100 || bioScore !in 0..100 || physScore !in 0..100 || chemScore !in 0..100 || engScore !in 0..100) {
                Toast.makeText(requireContext(), "Score value must be between 0 and 100.", Toast.LENGTH_SHORT).show()
            } else {
                val scores = listOf(
                    mathScore,
                    bioScore,
                    physScore,
                    chemScore,
                    engScore
                )

                sharedViewModel.updateScores(scores)
                dismiss()
            }
        }
    }
}