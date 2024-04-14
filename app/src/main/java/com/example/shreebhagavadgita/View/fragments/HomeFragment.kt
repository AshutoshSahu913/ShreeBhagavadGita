package com.example.shreebhagavadgita.View.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.shreebhagavadgita.DataSource.Models.Chapters
import com.example.shreebhagavadgita.ViewModel.MainViewModel
import com.example.shreebhagavadgita.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by viewModels()
//    private homeAdpater:HomeAdapter()

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
                for (i in it) {
                    Log.d("ITEMS", "getAllChapter: $i")
                }
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