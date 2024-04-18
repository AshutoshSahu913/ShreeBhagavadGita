package com.example.shreebhagavadgita.View.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shreebhagavadgita.DataSource.Models.ChaptersItem
import com.example.shreebhagavadgita.DataSource.Room.SavedChapterEntity
import com.example.shreebhagavadgita.R
import com.example.shreebhagavadgita.View.Adapters.ChaptersAdapter
import com.example.shreebhagavadgita.View.NetworkManager
import com.example.shreebhagavadgita.ViewModel.MainViewModel
import com.example.shreebhagavadgita.databinding.ExitDialogBinding
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

        checkInternetConnectivity()

        showVerseOfTheDay()

        binding.saveBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_saveFragment)
        }


//        findNavController().popBackStack()
        return binding.root
    }

    private fun showVerseOfTheDay() {
        val chapterNumber = (1..18).random()
        val verseNumber = (1..20).random()

        lifecycleScope.launch {
            viewModel.getParticularVerse(chapterNumber, verseNumber).collect {
                for (i in it.translations) {
                    if (i.language == "english") {
                        binding.tvVerseOfTheDay.text = i.description
                        break
                    }
                }
            }
        }

    }

    private fun checkInternetConnectivity() {

        val networkManager = NetworkManager(requireContext())

        networkManager.observe(viewLifecycleOwner) {

            if (it) {
                binding.topLinear.visibility = View.VISIBLE
                binding.shimmer.visibility = View.VISIBLE
                binding.rvChapters.visibility = View.VISIBLE
                binding.noInternet.visibility = View.GONE
                getAllChapter()
            } else {
                binding.shimmer.visibility = View.GONE
                binding.rvChapters.visibility = View.GONE
                binding.noInternet.visibility = View.VISIBLE
                binding.topLinear.visibility = View.INVISIBLE
            }
        }
    }


    private fun getAllChapter() {
        lifecycleScope.launch {

            viewModel.getAllChapter().collect {

                binding.rvChapters.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                chapterAdapter = ChaptersAdapter(
                    onClickedChapter = ::onChapterIVClicked,
                    onFavClicked = ::onFavClicked,
                    showFavBtn = true,
                    onFavClickedNot = ::onFavClickedNot,
                    viewModel
                )
                binding.rvChapters.adapter = chapterAdapter
                //here submit the list to adapter
                chapterAdapter.differ.submitList(it)
                binding.shimmer.visibility = View.GONE

                //here check single item in the log
//                for (i in it) {
//                    chapterAdapter
//                }

            }
        }
    }

    private fun onFavClicked(chaptersItem: ChaptersItem?) {

        lifecycleScope.launch {
            //check null here
            chaptersItem?.let {
                viewModel.putSavedChapterSP(chaptersItem.chapter_number.toString(), chaptersItem.id)
                viewModel.getVerses(it.chapter_number).collect {

                    //create list
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
                    val saveChapter = SavedChapterEntity(
                        chapter_number = chaptersItem.chapter_number,
                        chapter_summary = chaptersItem.chapter_summary,
                        chapter_summary_hindi = chaptersItem.chapter_summary_hindi,
                        id = chaptersItem.id,
                        name = chaptersItem.name,
                        name_meaning = chaptersItem.name_meaning,
                        name_translated = chaptersItem.name_translated,
                        name_transliterated = chaptersItem.name_transliterated,
                        slug = chaptersItem.slug,
                        verses_count = chaptersItem.verses_count,

                        verses = verseList

                    )
                    viewModel.insertData(saveChapter)
                    Toast.makeText(requireContext(), "Save Chapter", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun onFavClickedNot(chaptersItem: ChaptersItem?) {
        lifecycleScope.launch {
            viewModel.deleteSavedChapterSP(chaptersItem?.chapter_number.toString())
            if (chaptersItem != null) {
                viewModel.deleteSavedChapter(chaptersItem.id)
                Toast.makeText(requireContext(), "Unsaved chapter", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Handle back button press
            if (findNavController().currentDestination?.id == R.id.homeFragment) {
                // If the current fragment is HomeFragment, show a confirmation dialog
                showConfirmationDialog()
            } else {
                // If it's not HomeFragment, proceed with default behavior
                isEnabled = false
                requireActivity().onBackPressed()
            }
        }
    }

    fun onChapterIVClicked(chaptersItem: ChaptersItem?) {

        val bundle = Bundle()
        if (chaptersItem != null) {
            bundle.putInt("chapterNumber", chaptersItem.chapter_number)
            bundle.putString("chapterTitle", chaptersItem.name_translated)
            bundle.putString("chapterDesc", chaptersItem.chapter_summary)
            bundle.putInt("chapterCount", chaptersItem.verses_count)
        }

        findNavController().navigate(R.id.action_homeFragment_to_versesFragment, bundle)
    }

    private fun showConfirmationDialog() {
        // Inflate the custom layout for the dialog
        val dialogBinding = ExitDialogBinding.inflate(layoutInflater)

        // Create the dialog
        val dialog = Dialog(requireContext())
        dialog.setContentView(dialogBinding.root)

        // Configure click listeners for buttons
        dialogBinding.exitBtn.setOnClickListener {
            // If the user confirms, exit the app
            requireActivity().finish()
        }
        dialogBinding.cancelBtn.setOnClickListener {
            // If the user cancels, dismiss the dialog
            dialog.dismiss()
        }

        // Set dialog background to transparent
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Show the dialog
        dialog.show()

    }


}