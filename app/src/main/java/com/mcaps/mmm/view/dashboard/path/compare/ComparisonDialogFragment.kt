package com.mcaps.mmm.view.dashboard.path.compare

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.mcaps.mmm.R
import com.mcaps.mmm.data.api.response.MajorDataItem
import com.mcaps.mmm.databinding.FragmentCompareBinding
import com.mcaps.mmm.databinding.FragmentComparisonDialogBinding

class ComparisonDialogFragment : DialogFragment() {

    private var _binding: FragmentComparisonDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentComparisonDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val compareType = arguments?.getString(ARG_COMPARE_TYPE)
        val major1 = arguments?.getParcelable<MajorDataItem>(ARG_MAJOR1)
        val major2 = arguments?.getParcelable<MajorDataItem>(ARG_MAJOR2)

        if (compareType != null && major1 != null && major2 != null) {
            val title = "$compareType Comparison"
            val content1: String
            val content2: String
            val title1: String
            val title2: String

            when (compareType) {
                "Skills" -> {
                    content1 = major1.formattedSkillsRequired()
                    content2 = major2.formattedSkillsRequired()
                    title1 = "Skills Required for ${major1.title}"
                    title2 = "Skills Required for ${major2.title}"
                }
                "Career Prospects" -> {
                    content1 = major1.formattedCareerProspects()
                    content2 = major2.formattedCareerProspects()
                    title1 = "Career Prospects for ${major1.title}"
                    title2 = "Career Prospects for ${major2.title}"
                }
                else -> {
                    content1 = "Invalid comparison type"
                    content2 = "Invalid comparison type"
                    title1 = "Invalid Title"
                    title2 = "Invalid Title"
                }
            }

            binding.tvCompareTitle.text = title
            binding.tvCompareContent.text = content1
            binding.tvCompareContent2.text = content2
            binding.tvCompareContentTitle.text = title1
            binding.tvCompareContentTitle2.text = title2
        }
    }

    companion object {
        private const val ARG_COMPARE_TYPE = "compareType"
        private const val ARG_MAJOR1 = "major1"
        private const val ARG_MAJOR2 = "major2"

        fun newInstance(
            compareType: String, major1: MajorDataItem, major2: MajorDataItem
        ): ComparisonDialogFragment {
            val dialog = ComparisonDialogFragment()
            val args = Bundle().apply {
                putString(ARG_COMPARE_TYPE, compareType)
                putParcelable(ARG_MAJOR1, major1)
                putParcelable(ARG_MAJOR2, major2)
            }
            dialog.arguments = args
            return dialog
        }
    }
}

