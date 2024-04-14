package com.example.shreebhagavadgita.View.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shreebhagavadgita.DataSource.Models.Chapters
import com.example.shreebhagavadgita.View.Adapters.ChaptersAdapter
import com.example.shreebhagavadgita.ViewModel.MainViewModel
import com.example.shreebhagavadgita.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var chapterAdapter: ChaptersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        getAllChapter()
        return binding.root
    }

    private fun getAllChapter() {
        lifecycleScope.launch {

            viewModel.getAllChapter().collect {

                binding.rvChapters.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//                chapterAdapter = ChaptersAdapter(requireContext(), it)
                chapterAdapter = ChaptersAdapter()
                binding.rvChapters.adapter = chapterAdapter
                //here submit the list to adapter
                chapterAdapter.differ.submitList(it)
                binding.shimmer.visibility = View.GONE

                //here check single item in the log
//                for (i in it) {
//                    chapterAdapter
//                }
//                setRecyclerView(it)
            }
        }
    }

    private fun setRecyclerView(it: Chapters) {
//     val adapter=homeAdapter(requireActivity(),list)
    }

    companion object {
    }
}