package com.example.shreebhagavadgita.View.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.example.shreebhagavadgita.DataSource.Models.Commentary
import com.example.shreebhagavadgita.DataSource.Models.Translation
import com.example.shreebhagavadgita.DataSource.Models.VersesItem
import com.example.shreebhagavadgita.DataSource.Room.SavedVersesEntity
import com.example.shreebhagavadgita.R
import com.example.shreebhagavadgita.View.NetworkManager
import com.example.shreebhagavadgita.ViewModel.MainViewModel
import com.example.shreebhagavadgita.databinding.FragmentVersesDetailsBinding
import kotlinx.coroutines.launch

class VersesDetailsFragment : Fragment() {
    private lateinit var binding: FragmentVersesDetailsBinding
    private val viewModel: MainViewModel by viewModels()
    var chapterNum = 0
    var versesNum = 0
    private var verseDetail = MutableLiveData<VersesItem>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentVersesDetailsBinding.inflate(layoutInflater)

        getAndSetNumbers()

        readMore()

        getDataFromRoom()

        onSaveVerse()

        return binding.root
    }

    private fun getDataFromRoom() {
        val bundle = arguments
        val showDataFromRoomDB = bundle?.getBoolean("showRoomData", false)
//        val chapterNo = bundle?.getInt("chapterNum")
//        val verseNo = bundle?.getInt("versesNum")
        if (showDataFromRoomDB == true) {
            binding.verseSaveBtn.visibility = View.GONE
            viewModel.getAParticularVerse(chapterNum, versesNum).observe(viewLifecycleOwner) {

                binding.tvVerseTxt.text = it.text
                binding.tvTranslateEg.text = it.transliteration
                binding.tvVerseWords.text = it.word_meanings


                //first create list
                //create for the store all english & hindi verse
                val englishTranslationList = arrayListOf<Translation>()
//                val hindiTranslationList = arrayListOf<Translation>()

                for (i in it.translations) {
                    //fetch only english language verse
                    if (i.language == "english") {
                        englishTranslationList.add(i)
                    } /*else if (i.language == "hindi") {//this is for hindi translations
                        englishTranslationList.add(i)
                    }*/
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

                val commentaryList = arrayListOf<Commentary>()
                for (i in it.commentaries) {
                    commentaryList.add(i)
                }
                val commentaryListSize = commentaryList.size
                if (commentaryList.isNotEmpty()) {
                    binding.commentaryAuthor.text = commentaryList[0].author_name
                    binding.commentaryDesc.text = commentaryList[0].description
                    if (commentaryListSize == 1) {
                        binding.rightBtn2.setBackgroundResource(R.drawable.back_btn_dark)
                        binding.rightBtn2.isClickable = false
                    }
                    var i = 0
                    binding.rightBtn2.setOnClickListener {

                        if (i < commentaryListSize - 1) {
                            i++
                            binding.commentaryAuthor.text = commentaryList[i].author_name
                            binding.commentaryDesc.text = commentaryList[i].description
                            binding.leftBtn2.setBackgroundResource(R.drawable.back_btn)
                            binding.leftBtn2.isClickable = true
                            if (i == commentaryListSize - 1) {
                                binding.rightBtn2.setBackgroundResource(R.drawable.back_btn_dark)
                                binding.rightBtn2.isClickable = false
                            }
                        }
                    }

                    binding.leftBtn2.setOnClickListener {
                        if (i > 0) {
                            i--
                            binding.commentaryAuthor.text = commentaryList[i].author_name
                            binding.commentaryDesc.text = commentaryList[i].description
                            binding.rightBtn2.setBackgroundResource(R.drawable.back_btn)
                            binding.rightBtn2.isClickable = true
                            if (i == 0) {
                                binding.leftBtn2.setBackgroundResource(R.drawable.back_btn_dark)
                                binding.leftBtn2.isClickable = false
                            }
                        }
                    }
                }

                binding.progressBar.visibility = View.GONE
                binding.topBottomLL.visibility = View.VISIBLE
                binding.midLL.visibility = View.VISIBLE
                binding.bottomLL.visibility = View.VISIBLE
            }


        } else {
            //check the internet connectivity
            checkInternetConnectivity()
        }
    }

    private fun savingVerseDetails() {
        verseDetail.observe(viewLifecycleOwner) {
            viewModel.putSavedVersesSP(it.verse_number.toString(), it.verse_number)
            val englishTranslationList = arrayListOf<Translation>()
            for (i in it.translations) {
                //fetch only english language verse
                if (i.language == "english") {
                    englishTranslationList.add(i)
                }
            }

            val commentaryList = arrayListOf<Commentary>()
            for (i in it.commentaries) {
                commentaryList.add(i)
            }

            val savedVerses = SavedVersesEntity(
                it.chapter_number,
                commentaryList,
                it.id,
                it.slug,
                it.text,
                englishTranslationList,
                it.transliteration,
                it.verse_number,
                it.word_meanings,
            )
            //create coroutine scope
            lifecycleScope.launch {
                //insert verse data into database by a viewModel
                viewModel.insertEnglishVerse(savedVerses)
                Toast.makeText(requireContext(), "Saved Verse", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun onSaveVerse() {
        var isClicked = false
        val keys = viewModel.getAllSavedVersesKeySP()
        if (keys.contains(versesNum.toString())) {
            binding.verseSaveBtn.setImageResource(R.drawable.baseline_favorite_24)
            isClicked = true
        } else {
            binding.verseSaveBtn.setImageResource(R.drawable.baseline_favorite_border_24)
            isClicked = false
        }

        binding.verseSaveBtn.setOnClickListener {
            if (!isClicked) {
                binding.verseSaveBtn.setImageResource(R.drawable.baseline_favorite_24)
                isClicked = true
                savingVerseDetails()
            } else {
                lifecycleScope.launch {
                    binding.verseSaveBtn.setImageResource(R.drawable.baseline_favorite_border_24)
                    isClicked = false
                    viewModel.deleteSavedVersesSP(versesNum.toString())
                    viewModel.deleteSavedVerse(chapterNum, versesNum)
                    Toast.makeText(requireContext(), "Unsaved Verses", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun readMore() {
        var isExpended = false
        binding.readMore.setOnClickListener {
            if (!isExpended) {
                binding.commentaryDesc.maxLines = 150
                binding.readMore.text = "Less"
                isExpended = true
            } else {
                binding.commentaryDesc.maxLines = 5
                binding.readMore.text = "Read More..."
                isExpended = false
            }
        }
    }

    private fun checkInternetConnectivity() {

        val networkManager = NetworkManager(requireContext())
        networkManager.observe(viewLifecycleOwner) {
            if (it) {
                binding.noInternet.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
                binding.topBottomLL.visibility = View.VISIBLE
                binding.midLL.visibility = View.VISIBLE
                binding.bottomLL.visibility = View.VISIBLE
                getVerseDetails()
            } else {
                binding.progressBar.visibility = View.GONE
                binding.noInternet.visibility = View.VISIBLE
                binding.topBottomLL.visibility = View.GONE
                binding.midLL.visibility = View.GONE
                binding.bottomLL.visibility = View.GONE
            }
        }
    }


    private fun getVerseDetails() {
        lifecycleScope.launch {
            viewModel.getParticularVerse(chapterNum, versesNum).collect {
                verseDetail.postValue(it)
//                check data is fetch or not
//                for (i in it.translations)
//                Log.d("translations", "getVerseDetails: id : ${i.id}\n authorName : ${i.author_name}")

                binding.tvVerseTxt.text = it.text
                binding.tvTranslateEg.text = it.transliteration
                binding.tvVerseWords.text = it.word_meanings

                //first create list
                //create for the store all english & hindi verse
                val englishTranslationList = arrayListOf<Translation>()
//                val hindiTranslationList = arrayListOf<Translation>()

                for (i in it.translations) {
                    //fetch only english language verse
                    if (i.language == "english") {
                        englishTranslationList.add(i)
                    } /*else if (i.language == "hindi") {//this is for hindi translations
                        englishTranslationList.add(i)
                    }*/
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


                val commentaryList = arrayListOf<Commentary>()
                for (i in it.commentaries) {
                    commentaryList.add(i)
                }
                val commentaryListSize = commentaryList.size
                if (commentaryList.isNotEmpty()) {
                    binding.commentaryAuthor.text = commentaryList[0].author_name
                    binding.commentaryDesc.text = commentaryList[0].description
                    if (commentaryListSize == 1) {
                        binding.rightBtn2.setBackgroundResource(R.drawable.back_btn_dark)
                        binding.rightBtn2.isClickable = false
                    }
                    var i = 0
                    binding.rightBtn2.setOnClickListener {

                        if (i < commentaryListSize - 1) {
                            i++
                            binding.commentaryAuthor.text = commentaryList[i].author_name
                            binding.commentaryDesc.text = commentaryList[i].description
                            binding.leftBtn2.setBackgroundResource(R.drawable.back_btn)
                            binding.leftBtn2.isClickable = true
                            if (i == commentaryListSize - 1) {
                                binding.rightBtn2.setBackgroundResource(R.drawable.back_btn_dark)
                                binding.rightBtn2.isClickable = false
                            }
                        }
                    }

                    binding.leftBtn2.setOnClickListener {
                        if (i > 0) {
                            i--
                            binding.commentaryAuthor.text = commentaryList[i].author_name
                            binding.commentaryDesc.text = commentaryList[i].description
                            binding.rightBtn2.setBackgroundResource(R.drawable.back_btn)
                            binding.rightBtn2.isClickable = true
                            if (i == 0) {
                                binding.leftBtn2.setBackgroundResource(R.drawable.back_btn_dark)
                                binding.leftBtn2.isClickable = false
                            }
                        }
                    }
                }
            }
            binding.progressBar.visibility = View.GONE
            binding.topBottomLL.visibility = View.VISIBLE
            binding.midLL.visibility = View.VISIBLE
            binding.bottomLL.visibility = View.VISIBLE
        }
    }

    private fun getAndSetNumbers() {

        val bundle = arguments
        chapterNum = bundle?.getInt("chapterNum")!!
        versesNum = bundle.getInt("versesNum")

        binding.tvVersesNumber.text = "|| $chapterNum.$versesNum ||"
    }

}