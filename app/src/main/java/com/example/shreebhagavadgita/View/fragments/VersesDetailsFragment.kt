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
import com.example.shreebhagavadgita.ViewModel.MainViewModel
import com.example.shreebhagavadgita.databinding.FragmentVersesDetailsBinding
import kotlinx.coroutines.launch

class VersesDetailsFragment : Fragment() {


    private lateinit var binding: FragmentVersesDetailsBinding
    private val viewModel: MainViewModel by viewModels()
    var chapterNum = 0
    var versesNum = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVersesDetailsBinding.inflate(layoutInflater)

        getAndSetNumbers()

        getVerseDetails()
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    private fun getVerseDetails() {

        lifecycleScope.launch {
            viewModel.getParticularVerse(chapterNum, versesNum).collect {
                for (i in it.translations)
                    Log.d("translations", "getVerseDetails: id : ${i.id}\nauthorName : ${i.author_name}")
            }
        }
    }

    private fun getAndSetNumbers() {

        val bundle = arguments
        chapterNum = bundle?.getInt("chapterNum")!!
        versesNum = bundle.getInt("versesNum")

        binding.tvVersesNumber.text = "|| $chapterNum.$versesNum ||"


    }

}