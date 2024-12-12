package com.mcaps.mmm.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.mcaps.mmm.databinding.FragmentLoadingBinding

class LoadingDialogFragment : DialogFragment() {
    private var _binding: FragmentLoadingBinding? = null
    private val binding get() = checkNotNull(_binding) {  }

    private var ellipsisState = 0
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handler.postDelayed(object : Runnable {
            override fun run() {
                updateEllipsis()
                handler.postDelayed(this, 500)
            }
        }, 500)
    }

    private fun updateEllipsis() {
        ellipsisState = (ellipsisState + 1) % 4
        binding.ellipsis.text = when (ellipsisState) {
            0 -> ".  "
            1 -> ".. "
            2 -> "..."
            else -> "   "
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    companion object {
        fun newInstance(): LoadingDialogFragment {
            return LoadingDialogFragment()
        }
    }
}
