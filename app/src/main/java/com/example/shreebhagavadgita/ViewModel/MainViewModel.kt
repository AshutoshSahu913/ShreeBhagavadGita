package com.example.shreebhagavadgita.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.shreebhagavadgita.DataSource.Models.Chapters
import com.example.shreebhagavadgita.DataSource.Models.VersesItem
import com.example.shreebhagavadgita.DataSource.Room.AppDatabase
import com.example.shreebhagavadgita.DataSource.Room.SavedChapterEntity
import com.example.shreebhagavadgita.Repository.AppRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val saveChapterDao = AppDatabase.getDatabaseInstance(application).savedChapterDao()

    val appRepository = AppRepository(saveChapterDao)
    fun getAllChapter(): Flow<Chapters> = appRepository.getAllChapters()

    fun getVerses(chapterNumber: Int): Flow<List<VersesItem>> =
        appRepository.getVerses(chapterNumber)


    fun getParticularVerse(chapterNumber: Int, verseNumber: Int): Flow<VersesItem> =
        appRepository.getParticularVerse(chapterNumber, verseNumber)

    //save chapters
    suspend fun insertData(savedChapter: SavedChapterEntity) =
        appRepository.insertData(savedChapter)

    //get saved chpaters
    fun getSavedChapter(): LiveData<List<SavedChapterEntity>> = appRepository.getSavedChapter()
}