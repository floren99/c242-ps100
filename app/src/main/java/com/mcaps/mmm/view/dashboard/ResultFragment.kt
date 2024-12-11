package com.mcaps.mmm.view.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.mcaps.mmm.R
import com.mcaps.mmm.databinding.FragmentLoadingBinding
import com.mcaps.mmm.databinding.FragmentResultBinding

class ResultFragment : DialogFragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = checkNotNull(_binding) {  }

    companion object {
        fun newInstance(result: String, imageResId: Int): ResultFragment {
            val fragment = ResultFragment()
            val args = Bundle()
            args.putString("RESULT", result)

            val imageResId = when (result) {
                "Aktuari" -> R.drawable.lg_aktuaria
                "Farmasi" -> R.drawable.lg_farmasi
                "Ilmu Komputer" -> R.drawable.lg_ilmu_komputer
                "Ilmu Kedokteran" -> R.drawable.lg_teknik_kedokteran
                "Keperawatan" -> R.drawable.lg_keperawatan
                "Matematika" -> R.drawable.lg_matematika
                "Teknik Elektro" -> R.drawable.lg_teknik_elektro
                "Teknik Kelautan" -> R.drawable.lg_teknik_kelautan
                "Teknik Sipil" -> R.drawable.lg_teknik_sipil
                "Teknologi Pangan" -> R.drawable.lg_teknologi_pangan
                else -> R.drawable.ic_baseline_android_24
            }
            args.putInt("IMAGE_RES_ID", imageResId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val result = arguments?.getString("RESULT")
        val imageResId = arguments?.getInt("IMAGE_RES_ID")

        binding.textResult.text = result ?: "No prediction available"
        binding.imageResult.setImageResource(imageResId ?: R.drawable.ic_baseline_android_24)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}