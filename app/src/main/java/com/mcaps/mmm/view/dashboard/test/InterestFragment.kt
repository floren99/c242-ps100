package com.mcaps.mmm.view.dashboard.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.lifecycle.ViewModelProvider
import com.mcaps.mmm.R
import com.mcaps.mmm.databinding.FragmentInterestBinding
import com.mcaps.mmm.view.ViewModelFactory

class InterestFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentInterestBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val sharedViewModel: TestViewModel by lazy{
        ViewModelProvider(this, ViewModelFactory.getInstance(requireContext()))[TestViewModel::class.java]
    }

    private val questions = listOf(
        "1. Apakah kamu mempunyai ketertarikan dalam hal pemrograman?",
        "2. Apakah kamu mempunyai ketertarikan dalam hal analisis data?",
        "3. Apakah kamu mempunyai ketertarikan dalam hal pemecahan masalah?",
        "4. Apakah kamu merasa memiliki kemampuan kreatifitas dan bersosialisasi?",
        "5. Apakah kamu mempunyai ketertarikan dalam hal pekerjaan lapangan?",
        "6. Apakah kamu merasa mempunyai keterampilan dalam hal kepemimpinan?",
        "7. Apakah kamu mempunyai ketertarikan dalam hal pekerjaan praktik?"
    )

    private val answers = MutableList(questions.size) { -1 }
    private var currentQuestionIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInterestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateQuestion()

        binding.prevButton.setOnClickListener {
            saveAnswer()
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--
                updateQuestion()
            }
        }

        binding.nextButton.setOnClickListener {
            saveAnswer()
            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
                updateQuestion()
            } else {
                sharedViewModel.updateMinat(answers)
                dismiss()
            }
        }
    }

    private fun updateQuestion() {
        binding.questionTextView.text = questions[currentQuestionIndex]
        binding.answerRadioGroup.clearCheck()

        when (answers[currentQuestionIndex]) {
            0 -> binding.radioNo.isChecked = true
            1 -> binding.radioYes.isChecked = true
        }

        binding.prevButton.isEnabled = currentQuestionIndex > 0
        binding.nextButton.text = if (currentQuestionIndex == questions.size - 1) "Submit" else "Next"
    }

    private fun saveAnswer() {
        val selectedOption = when (binding.answerRadioGroup.checkedRadioButtonId) {
            R.id.radioNo -> 0
            R.id.radioYes -> 1
            else -> -1
        }
        if (selectedOption != -1) {
            answers[currentQuestionIndex] = selectedOption
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
