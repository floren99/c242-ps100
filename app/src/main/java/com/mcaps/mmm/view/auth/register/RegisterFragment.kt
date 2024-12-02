package com.mcaps.mmm.view.auth.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mcaps.mmm.R
import com.mcaps.mmm.databinding.FragmentHomeBinding
import com.mcaps.mmm.databinding.FragmentRegisterBinding

class RegisterFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = checkNotNull(_binding) {  }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playAnimation()
        binding.regButton.setOnClickListener {
            dismiss()
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
}