package com.example.shreebhagavadgita.View.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shreebhagavadgita.R
import com.example.shreebhagavadgita.View.Adapters.VersesAdapter
import com.example.shreebhagavadgita.View.NetworkManager
import com.example.shreebhagavadgita.ViewModel.MainViewModel
import com.example.shreebhagavadgita.databinding.FragmentVersesBinding
import kotlinx.coroutines.launch

class VersesFragment : Fragment() {
    private lateinit var binding: FragmentVersesBinding
    private val viewModel: MainViewModel by viewModels()
    private var verseAdapter = VersesAdapter(::onVersesItemClicked)
    private var chapterNumber = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentVersesBinding.inflate(layoutInflater)

        //get a chapter details from previous fragment
        getAndSetChaptersDetails()

        //click the see more show all data
        readMore()

        //check the internet connectivity
        checkInternetConnectivity()
        return binding.root
    }

    private fun readMore() {
        var isExpended = false
        binding.readMore.setOnClickListener {
            if (!isExpended) {
                binding.tvChapterDes.maxLines = 40
                binding.readMore.text = "Less"
                isExpended = true
            } else {
                binding.tvChapterDes.maxLines = 5
                binding.readMore.text = "Read More..."
                isExpended = false
            }
        }
    }

    private fun checkInternetConnectivity() {
        val networkManager = NetworkManager(requireContext())
        networkManager.observe(viewLifecycleOwner) {
            if (it) {
                binding.shimmerVerses.visibility = View.VISIBLE
                binding.rvVerses.visibility = View.VISIBLE
                binding.topLinear.visibility = View.VISIBLE
                binding.noInternet.visibility = View.GONE

                //get all the verses of according to chapter number
                getAllVerses()

            } else {
                binding.shimmerVerses.visibility = View.GONE
                binding.rvVerses.visibility = View.GONE
                binding.topLinear.visibility = View.INVISIBLE
                binding.noInternet.visibility = View.VISIBLE
            }
        }
    }

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
                verseAdapter = VersesAdapter(::onVersesItemClicked)
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

    private fun onVersesItemClicked(verses: String, versesNumber: Int) {

        var bundle = Bundle()
        bundle.putInt("chapterNum", chapterNumber)
        bundle.putInt("versesNum", versesNumber)
        findNavController().navigate(R.id.action_versesFragment_to_versesDetailsFragment, bundle)

    }
}