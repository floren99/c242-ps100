package com.mcaps.mmm.view.auth.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.AlertDialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mcaps.mmm.R
import com.mcaps.mmm.databinding.FragmentHomeBinding
import com.mcaps.mmm.databinding.FragmentRegisterBinding
import com.mcaps.mmm.view.ViewModelFactory

class RegisterFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = checkNotNull(_binding) {  }
    private val registerViewModel: RegisterViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playAnimation()
        binding.regButton.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()

            if (validateInput(name, email, password)) {
                registerUser(name, email, password)
            }
        }

        registerViewModel.registerResponse.observe(viewLifecycleOwner) { response ->
            if (!response.error!!) {
                Toast.makeText(requireContext(), "Registration Successful", Toast.LENGTH_SHORT).show()
                dismiss()  // Close the bottom sheet after successful registration
            } else {
                showErrorDialog("Registration failed: ${response.message}")
            }
        }

        registerViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressReg.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        registerViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                showErrorDialog(it)
            }
        }
    }

    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(300)
        val tvName = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(300)
        val ivName = ObjectAnimator.ofFloat(binding.edRegisterName, View.ALPHA, 1f).setDuration(300)
        val lName = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val tvEmail = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(300)
        val ivEmail = ObjectAnimator.ofFloat(binding.edRegisterEmail, View.ALPHA, 1f).setDuration(300)
        val lEmail = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val tvPass = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(300)
        val ivPass = ObjectAnimator.ofFloat(binding.edRegisterPassword, View.ALPHA, 1f).setDuration(300)
        val lPass = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val reg = ObjectAnimator.ofFloat(binding.regButton, View.ALPHA, 1f).setDuration(300)

        val name = AnimatorSet().apply {
            playTogether(tvName, ivName, lName)
        }
        val email = AnimatorSet().apply {
            playTogether(tvEmail, ivEmail, lEmail)
        }
        val pass = AnimatorSet().apply {
            playTogether(tvPass, ivPass, lPass)
        }

        AnimatorSet().apply {
            playSequentially(title, name, email, pass, reg)
            startDelay = 200
            start()
        }
    }

    private fun registerUser(name: String, email: String, password: String) {
        registerViewModel.registerUser(name, email, password)
    }

    private fun validateInput(name: String, email: String, password: String): Boolean {
        return when {
            name.isBlank() -> {
                showErrorDialog("Name cannot be empty")
                false
            }
            email.isBlank() -> {
                showErrorDialog("Email cannot be empty")
                false
            }
            password.length < 8 -> {
                showErrorDialog("Password must be at least 8 characters")
                false
            }
            else -> true
        }
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