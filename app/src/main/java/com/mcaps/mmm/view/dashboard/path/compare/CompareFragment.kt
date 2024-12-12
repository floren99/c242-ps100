package com.mcaps.mmm.view.dashboard.path.compare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mcaps.mmm.R
import com.mcaps.mmm.data.api.response.MajorDataItem
import com.mcaps.mmm.databinding.FragmentCompareBinding
import com.mcaps.mmm.view.ViewModelFactory
import com.mcaps.mmm.view.dashboard.path.PathViewModel

class CompareFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentCompareBinding? = null
    private val binding get() = _binding!!

    private val pathViewModel: PathViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private var majors: List<MajorDataItem> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompareBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeMajors()
        setupActions()
    }

    private fun observeMajors() {
        pathViewModel.pathList.observe(viewLifecycleOwner) { majorList ->
            majors = majorList
            val titles = majorList.map { it.title ?: "Unknown" }
            val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, titles)
            adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            binding.spinnerMajor1.adapter = adapter
            binding.spinnerMajor2.adapter = adapter
        }
    }

    private fun setupActions() {
        binding.btnCompareSkills.setOnClickListener {
            compare("Skills")
        }

        binding.btnCompareJobs.setOnClickListener {
            compare("Career Prospects")
        }
    }

    private fun compare(compareType: String) {
        val selectedMajor1 = binding.spinnerMajor1.selectedItemPosition
        val selectedMajor2 = binding.spinnerMajor2.selectedItemPosition

        if (selectedMajor1 >= 0 && selectedMajor2 >= 0) {
            val major1 = majors[selectedMajor1]
            val major2 = majors[selectedMajor2]

            ComparisonDialogFragment.newInstance(compareType, major1, major2)
                .show(parentFragmentManager, "ComparisonDialogFragment")
        } else {
            Toast.makeText(requireContext(), "Please select both majors", Toast.LENGTH_SHORT).show()
        }
    }
}
