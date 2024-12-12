package com.mcaps.mmm.view.auth.reset

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mcaps.mmm.databinding.FragmentResetPassBinding
import com.mcaps.mmm.view.ViewModelFactory

class ResetPassFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentResetPassBinding? = null
    private val binding get() = checkNotNull(_binding) {  }
    private val resetPassViewModel: ResetPassViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResetPassBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playAnimation()
        binding.resetButton.setOnClickListener {
            val email = binding.edResetEmail.text.toString()
            resetPass(email)
        }

        resetPassViewModel.resetResponse.observe(viewLifecycleOwner) { response ->
            if (!response.error!!) {
                Toast.makeText(requireContext(), "Email has been sent, check your Email", Toast.LENGTH_SHORT).show()
                dismiss()
            } else {
                showErrorDialog("Registration failed: ${response.message}")
            }
        }

        resetPassViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressReg.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        resetPassViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                showErrorDialog(it)
            }
        }
    }

    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(300)
        val tvEmail = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(300)
        val ivEmail = ObjectAnimator.ofFloat(binding.edResetEmail, View.ALPHA, 1f).setDuration(300)
        val lEmail = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val reset = ObjectAnimator.ofFloat(binding.resetButton, View.ALPHA, 1f).setDuration(300)

        val email = AnimatorSet().apply {
            playTogether(tvEmail, ivEmail, lEmail)
        }

        AnimatorSet().apply {
            playSequentially(title, email, reset)
            startDelay = 200
            start()
        }
    }

    private fun resetPass(email: String) {
        resetPassViewModel.resetPass(email)
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Error")
            setMessage(message)
            setPositiveButton("OK") { _, _ -> }
            create()
            show()
        }
    }
}