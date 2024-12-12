package com.mcaps.mmm.view.dashboard.test

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.mcaps.mmm.R
import com.mcaps.mmm.data.pref.PredictRequest
import com.mcaps.mmm.databinding.FragmentTestBinding
import com.mcaps.mmm.view.LoadingDialogFragment
import com.mcaps.mmm.view.ViewModelFactory
import com.mcaps.mmm.view.dashboard.ResultFragment
import com.mcaps.mmm.view.dashboard.test.history.HistoryTestActivity
import com.mcaps.mmm.view.question.QuizActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestFragment : Fragment() {
    private val sharedViewModel: TestViewModel by lazy{
        ViewModelProvider(this, ViewModelFactory.getInstance(requireContext()))[TestViewModel::class.java]
    }

    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.scores.observe(viewLifecycleOwner) { scores ->
            if (scores.isNotEmpty()) {
                binding.done1.text = "Done"
            } else  {
                binding.done1.text = "Empty"
            }
        }

        sharedViewModel.quiz1.observe(viewLifecycleOwner) { quiz1 ->
            if (quiz1 != 0) {
                binding.done2.text = "Latest Result : " + "${quiz1}"
                binding.quizBtn1.text = "Retake"
            } else {
                binding.done2.text = "Empty"
            }
        }

        sharedViewModel.quiz2.observe(viewLifecycleOwner) { quiz2 ->
            if (quiz2 != 0) {
                binding.done3.text = "Latest Result : " + "${quiz2}"
                binding.quizBtn2.text = "Retake"
            } else {
                binding.done3.text = "Empty"
            }
        }

        sharedViewModel.quiz3.observe(viewLifecycleOwner) { quiz3 ->
            if (quiz3 != 0) {
                binding.done4.text = "Latest Result : " + "${quiz3}"
                binding.quizBtn3.text = "Retake"
            } else {
                binding.done4.text = "Empty"
            }
        }

        sharedViewModel.quiz4.observe(viewLifecycleOwner) { quiz4 ->
            if (quiz4 != 0) {
                binding.done5.text = "Latest Result : " + "${quiz4}"
                binding.quizBtn4.text = "Retake"
            } else {
                binding.done5.text = "Empty"
            }
        }

        sharedViewModel.minat.observe(viewLifecycleOwner) { minat ->
            if (minat.isNotEmpty()) {
                binding.done6.text = "Done"
            } else  {
                binding.done6.text = "Empty"
            }
        }

        sharedViewModel.answers.let { answers ->
            answers.forEachIndexed { index, answer ->
                println("Question ${index + 1}: Answer $answer")
            }
        }

        binding.buttonInputNilai.setOnClickListener{
            val scoreFragment = ScoreFragment()
            scoreFragment.show(parentFragmentManager, "ScoreFragment")
        }
        binding.buttonInputMinat.setOnClickListener{
            val interestFragment = InterestFragment()
            interestFragment.show(parentFragmentManager, "InterestFragment")
        }
        binding.quizBtn1.setOnClickListener{
            val intent = Intent(activity, QuizActivity::class.java)
            intent.putExtra("QUIZ_ID", "1")
            startActivity(intent)
        }
        binding.quizBtn2.setOnClickListener{
            val intent = Intent(activity, QuizActivity::class.java)
            intent.putExtra("QUIZ_ID", "2")
            startActivity(intent)
        }
        binding.quizBtn3.setOnClickListener{
            val intent = Intent(activity, QuizActivity::class.java)
            intent.putExtra("QUIZ_ID", "3")
            startActivity(intent)
        }
        binding.quizBtn4.setOnClickListener{
            val intent = Intent(activity, QuizActivity::class.java)
            intent.putExtra("QUIZ_ID", "4")
            startActivity(intent)
        }
        binding.btnHistory.setOnClickListener{
            val intent = Intent(activity, HistoryTestActivity::class.java)
            startActivity(intent)
        }

        binding.buttonCek.setOnClickListener {
            val scores = sharedViewModel.scores.value ?: emptyList()
            val minat = sharedViewModel.minat.value ?: emptyList()

            if (scores.isEmpty() || minat.isEmpty()) {
                Toast.makeText(requireContext(), "Fill at least Scores and Interest", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val finalInput = sharedViewModel.getFinalInput()
            val request = PredictRequest(finalInput)

            val loadingDialog = LoadingDialogFragment.newInstance()
            loadingDialog.isCancelable = false
            loadingDialog.show(parentFragmentManager, "LoadingDialog")

            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    delay(3000)
                    val response = sharedViewModel.predict(request)
                    sharedViewModel.saveDataToDatabase("${response.predictedLabel}")
                    loadingDialog.dismiss()
                    val resultDialog = ResultFragment.newInstance("${response.predictedLabel}", R.drawable.ic_baseline_android_24)
                    resultDialog.show(parentFragmentManager, "ResultDialog")
                } catch (e: Exception) {
                    e.printStackTrace()
                    loadingDialog.dismiss()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}