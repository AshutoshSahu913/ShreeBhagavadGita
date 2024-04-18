package com.example.shreebhagavadgita.View.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shreebhagavadgita.DataSource.Models.ChaptersItem
import com.example.shreebhagavadgita.View.Adapters.ChaptersAdapter
import com.example.shreebhagavadgita.ViewModel.MainViewModel
import com.example.shreebhagavadgita.databinding.FragmentSaveChaptersBinding

class SaveChaptersFragment : Fragment() {

    lateinit var binding: FragmentSaveChaptersBinding
    val viewModel: MainViewModel by viewModels()
    lateinit var chapterAdapter: ChaptersAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSaveChaptersBinding.inflate(layoutInflater)

        getSavedChapters()

        return binding.root
    }

    private fun getSavedChapters() {
        viewModel.getSavedChapter().observe(viewLifecycleOwner) {
            val chaptersList = arrayListOf<ChaptersItem>()
            for (i in it) {
                val chapterItems = ChaptersItem(
                    i.chapter_number,
                    i.chapter_summary,
                    i.chapter_summary_hindi,
                    i.id,
                    i.name,
                    i.name_meaning,
                    i.name_translated,
                    i.name_transliterated,
                    i.slug,
                    i.verses_count
                )
                chaptersList.add(chapterItems)
            }

            if (chaptersList.isEmpty()) {
                binding.shimmer.visibility = View.GONE
                binding.rvChapters.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                binding.noDataSaved.visibility = View.VISIBLE
            }
            binding.rvChapters.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            chapterAdapter = ChaptersAdapter(onClickedChapter = ::onClickedChapter, null,false)
            binding.rvChapters.adapter = chapterAdapter
            chapterAdapter.differ.submitList(chaptersList)

            binding.shimmer.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
            binding.rvChapters.visibility = View.VISIBLE
        }
    }

    fun onClickedChapter(chaptersItem: ChaptersItem?) {


    }

}