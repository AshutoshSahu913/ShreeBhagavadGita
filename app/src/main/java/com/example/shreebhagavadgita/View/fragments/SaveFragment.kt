package com.example.shreebhagavadgita.View.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
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



        setStatusBarColor()
        binding.savedVerses.setOnClickListener {
            findNavController().navigate(R.id.action_saveFragment_to_saveVersesFragment)
        }

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

}