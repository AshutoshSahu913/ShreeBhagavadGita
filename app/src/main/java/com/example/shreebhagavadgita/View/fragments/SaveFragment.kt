package com.example.shreebhagavadgita.View.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.shreebhagavadgita.R
import com.example.shreebhagavadgita.databinding.FragmentSaveBinding

class SaveFragment : Fragment() {
    private lateinit var binding: FragmentSaveBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentSaveBinding.inflate(layoutInflater)

        binding.savedChapters.setOnClickListener {
            findNavController().navigate(R.id.action_saveFragment_to_saveChaptersFragment)
        }



        binding.savedVerses.setOnClickListener {
            findNavController().navigate(R.id.action_saveFragment_to_saveVersesFragment)
        }

        return binding.root
    }

}