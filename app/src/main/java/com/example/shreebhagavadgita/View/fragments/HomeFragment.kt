package com.example.shreebhagavadgita.View.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.addCallback
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shreebhagavadgita.DataSource.Models.Chapters
import com.example.shreebhagavadgita.R
import com.example.shreebhagavadgita.View.Adapters.ChaptersAdapter
import com.example.shreebhagavadgita.ViewModel.MainViewModel
import com.example.shreebhagavadgita.databinding.ExitDialogBinding
import com.example.shreebhagavadgita.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: com.example.shreebhagavadgita.databinding.FragmentHomeBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var chapterAdapter: ChaptersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        getAllChapter()

//        findNavController().popBackStack()
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

    @SuppressLint("MissingInflatedId")
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

        // Apply blur effect to the background
//        val blurLayout = Dialog(requireContext())
//        blurLayout.setBlurredView(view as ViewGroup, dialog.window)

        // Set dialog parameters: height, width, and blur effect
//        val lp = WindowManager.LayoutParams()
//        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
//        lp.flags = WindowManager.LayoutParams.FLAG_BLUR_BEHIND
//        dialog.window?.attributes = lp

        // Show the dialog
        dialog.show()

    }

    companion object {
    }
}