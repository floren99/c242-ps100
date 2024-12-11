package com.mcaps.mmm.view.dashboard.path

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mcaps.mmm.databinding.FragmentPathBinding
import com.mcaps.mmm.view.MainActivity
import com.mcaps.mmm.view.ViewModelFactory
import com.mcaps.mmm.view.dashboard.path.compare.CompareFragment
import kotlinx.coroutines.launch

class PathFragment : Fragment() {

    private var _binding: FragmentPathBinding? = null
    private val binding get() = _binding!!

    private val pathViewModel: PathViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private lateinit var pathAdapter: PathAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPathBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pathAdapter = PathAdapter(requireContext())
        binding.rvPaths.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = pathAdapter
        }

        binding.compareButton.setOnClickListener{
            val compareFragment = CompareFragment()
            compareFragment.show(parentFragmentManager, "CompareFragment")
        }

        observeViewModel()
        fetchPaths()
    }

    private fun observeViewModel() {
        pathViewModel.pathList.observe(viewLifecycleOwner, Observer { paths ->
            pathAdapter.submitList(paths)
        })

        pathViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.progressBarPath.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        pathViewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchPaths() {
        lifecycleScope.launch {
            try {
                pathViewModel.getPaths()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}