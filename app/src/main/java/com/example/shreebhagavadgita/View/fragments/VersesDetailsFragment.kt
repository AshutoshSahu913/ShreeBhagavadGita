package com.example.shreebhagavadgita.View.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.shreebhagavadgita.DataSource.Models.Translation
import com.example.shreebhagavadgita.R
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
//                check data is fetch or not
//                for (i in it.translations)
//                    Log.d("translations", "getVerseDetails: id : ${i.id}\nauthorName : ${i.author_name}")

                binding.tvVerseTxt.text = it.text
                binding.tvTranslateEg.text = it.transliteration
                binding.tvVerseWords.text = it.word_meanings


                //first create list
                //create for the store all english & hindi verse
                val englishTranslationList = arrayListOf<Translation>()
                val hindiTranslationList = arrayListOf<Translation>()

                for (i in it.translations) {
                    //fetch only english language verse
                    if (i.language == "english") {
                        englishTranslationList.add(i)
                    } else if (i.language == "hindi") {
                        hindiTranslationList.add(i)
                    }
                }
                val englishTransSize = englishTranslationList.size
                if (englishTranslationList.isNotEmpty()) {
                    //set data
                    binding.tvAuthorName.text = englishTranslationList[0].author_name
                    binding.translationTxt.text = englishTranslationList[0].description
                    //
                    if (englishTransSize == 1) {
                        binding.rightBtn.setBackgroundResource(R.drawable.back_btn_dark)
                        binding.rightBtn.isClickable = false
                    }
                    //click btn event
                    var i = 0
                    binding.rightBtn.setOnClickListener {
                        if (i < englishTransSize - 1) {
                            i++
                            binding.tvAuthorName.text = englishTranslationList[i].author_name
                            binding.translationTxt.text = englishTranslationList[i].description
                            binding.leftBtn.setBackgroundResource(R.drawable.back_btn)
                            binding.leftBtn.isClickable = true
                            if (i == englishTransSize - 1) {
                                binding.rightBtn.setBackgroundResource(R.drawable.back_btn_dark)
                                binding.rightBtn.isClickable = false
                            }
                        }
                    }

                    binding.leftBtn.setOnClickListener {
                        if (i > 0) {
                            i--
                            binding.tvAuthorName.text = englishTranslationList[i].author_name
                            binding.translationTxt.text = englishTranslationList[i].description
                            binding.rightBtn.setBackgroundResource(R.drawable.back_btn)
                            binding.rightBtn.isClickable = true
                            if (i == 0) {
                                binding.leftBtn.setBackgroundResource(R.drawable.back_btn_dark)
                                binding.leftBtn.isClickable = false
                            }
                        }
                    }
                }
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