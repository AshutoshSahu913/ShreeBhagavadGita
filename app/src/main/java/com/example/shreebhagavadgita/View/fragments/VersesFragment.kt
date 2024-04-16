package com.example.shreebhagavadgita.View.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shreebhagavadgita.View.Adapters.VersesAdapter
import com.example.shreebhagavadgita.ViewModel.MainViewModel
import com.example.shreebhagavadgita.databinding.FragmentVersesBinding
import kotlinx.coroutines.launch

class VersesFragment : Fragment() {
    private lateinit var binding: FragmentVersesBinding
    private val viewModel: MainViewModel by viewModels()
    private var verseAdapter = VersesAdapter()
    private var chapterNumber = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentVersesBinding.inflate(layoutInflater)

        //get a chapter details from previous fragment
        getAndSetChaptersDetails()

        //get all the verses of according to chapter number
        getAllVerses()

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun getAndSetChaptersDetails() {
        val bundle = arguments
        chapterNumber = bundle?.getInt("chapterNumber")!!
        binding.tvChapterNumber.text = "Chapter $chapterNumber"
        binding.tvChapterTitle.text = bundle.getString("chapterTitle")
        binding.tvChapterDes.text = bundle.getString("chapterDesc")
        binding.tvVerseCount.text = bundle.getInt("chapterCount").toString()

    }

    private fun getAllVerses() {
        lifecycleScope.launch {
            viewModel.getVerses(chapterNumber).collect {
                binding.rvVerses.layoutManager = LinearLayoutManager(
                    requireContext(), LinearLayoutManager.VERTICAL, false
                )
                //create list and pass to adapter
                val verseList = arrayListOf<String>()
                for (currentVerse in it) {
                    for (verses in currentVerse.translations) {
                        //check only for english language
                        if (verses.language == "english") {
                            verseList.add(verses.description)
                            Log.d("VERSE_LIST", "getAllVerses: ${verseList.size}")
                            break
                        }
                    }
                }
                Log.d("LISTS", "getAllVerses: ${verseList.size}")
                binding.rvVerses.adapter = verseAdapter
                //get list to adpater
                verseAdapter.differ.submitList(verseList)

                //hide shimmer after data in set
                binding.shimmerVerses.visibility = View.GONE

//                fetch data from api
                /*for (i in it) {
                    Log.d("VERSE", "getAllVerses: $i")
                }*/
            }
        }
    }
}