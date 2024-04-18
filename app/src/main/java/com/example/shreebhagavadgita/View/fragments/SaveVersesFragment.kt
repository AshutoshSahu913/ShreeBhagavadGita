package com.example.shreebhagavadgita.View.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shreebhagavadgita.DataSource.Room.SavedVersesEntity
import com.example.shreebhagavadgita.R
import com.example.shreebhagavadgita.View.Adapters.AdapterSavedVerses
import com.example.shreebhagavadgita.ViewModel.MainViewModel
import com.example.shreebhagavadgita.databinding.FragmentSaveVersesBinding

class SaveVersesFragment : Fragment() {
    private lateinit var binding: FragmentSaveVersesBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapterSavedVerses: AdapterSavedVerses

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentSaveVersesBinding.inflate(layoutInflater)

        getVerseFromRoom()
        setStatusBarColor()
        return binding.root
    }

    private fun setStatusBarColor() {
        val window = activity?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.appColor)
        window?.let {
            WindowCompat.getInsetsController(it, window.decorView).apply {
                isAppearanceLightStatusBars = true
            }
        }
    }


    private fun getVerseFromRoom() {

        viewModel.getAllEnglishVerse().observe(viewLifecycleOwner) {

            binding.rvVerses.layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false
            )
            adapterSavedVerses = AdapterSavedVerses(::onVersesItemClicked)
            binding.rvVerses.adapter = adapterSavedVerses
            //get list to adpater
            adapterSavedVerses.differ.submitList(it)

            if (it.isEmpty()) {
                binding.progressBar.visibility = View.GONE
                binding.noDataSaved.visibility = View.VISIBLE
            }
            //hide shimmer after data in set
            binding.shimmerVerses.visibility = View.GONE
            binding.progressBar.visibility = View.GONE


        }

    }


    fun onVersesItemClicked(savedVerses: SavedVersesEntity) {

        var bundle = Bundle()
        bundle.putBoolean("showRoomData", true)
        bundle.putInt("chapterNum", savedVerses.chapter_number)
        bundle.putInt("versesNum", savedVerses.verse_number)
        findNavController().navigate(R.id.action_saveVersesFragment_to_versesDetailsFragment, bundle)
    }


}