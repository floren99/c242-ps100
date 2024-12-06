package com.mcaps.mmm.view.dashboard.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.mcaps.mmm.data.pref.PredictRequest
import com.mcaps.mmm.databinding.FragmentTestBinding
import com.mcaps.mmm.view.ViewModelFactory
import com.mcaps.mmm.view.auth.register.RegisterFragment
import com.mcaps.mmm.view.dashboard.path.PathViewModel
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
            binding.textTestHasil.text = scores.joinToString(", ") {
                "${it}"
            }
        }
        sharedViewModel.minat.observe(viewLifecycleOwner) { minat ->
            binding.textTestMinat.text = minat.joinToString(", "){
                "${it}"
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

        binding.buttonCek.setOnClickListener {
            val input = listOf(46, 67, 74, 75, 74, 19, 70, 73, 76, 1, 0, 1, 0, 0, 0, 1)
            val request = PredictRequest(input)
            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    val response = sharedViewModel.predict(request)
                    println("Prediction response: $response")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}