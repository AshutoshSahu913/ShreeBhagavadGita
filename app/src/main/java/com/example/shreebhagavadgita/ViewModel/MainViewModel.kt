package com.example.shreebhagavadgita.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.shreebhagavadgita.DataSource.Models.Chapters
import com.example.shreebhagavadgita.DataSource.Models.VersesItem
import com.example.shreebhagavadgita.DataSource.Room.AppDatabase
import com.example.shreebhagavadgita.DataSource.Room.SavedChapterEntity
import com.example.shreebhagavadgita.DataSource.Room.SavedVersesEntity
import com.example.shreebhagavadgita.DataSource.SharedPrep.SharedPreferencesManager
import com.example.shreebhagavadgita.Repository.AppRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val saveChapterDao = AppDatabase.getDatabaseInstance(application).savedChapterDao()

    val saveVerseDao = AppDatabase.getDatabaseInstance(application).savedVersesDao()

    val sharedPreferencesManager = SharedPreferencesManager(application)

    val appRepository = AppRepository(saveChapterDao, saveVerseDao, sharedPreferencesManager)

    //data fetch from API -------------------------------------------------------------------------------------------------
    fun getAllChapter(): Flow<Chapters> = appRepository.getAllChapters()

    fun getVerses(chapterNumber: Int): Flow<List<VersesItem>> =
        appRepository.getVerses(chapterNumber)


    fun getParticularVerse(chapterNumber: Int, verseNumber: Int): Flow<VersesItem> =
        appRepository.getParticularVerse(chapterNumber, verseNumber)

    //save chapters in database-------------------------------------------------------------------------------------------------

    suspend fun insertData(savedChapter: SavedChapterEntity) =
        appRepository.insertData(savedChapter)

    //get saved chapters
    fun getSavedChapter(): LiveData<List<SavedChapterEntity>> = appRepository.getSavedChapter()

    //delete saved chapters
    suspend fun deleteSavedChapter(id: Int) = appRepository.deleteSavedChapter(id)

    fun getAParticularChapter(chapter_num: Int): LiveData<SavedChapterEntity> =
        appRepository.getAParticularChapter(chapter_num)


    //save verses in database-------------------------------------------------------------------------------------------------
    suspend fun insertEnglishVerse(savedVerses: SavedVersesEntity) =
        appRepository.insertEnglishVerse(savedVerses)

    fun getAllEnglishVerse(): LiveData<List<SavedVersesEntity>> =
        appRepository.getAllEnglishVerse()

    fun getAParticularVerse(chapter_num: Int, verse_num: Int): LiveData<SavedVersesEntity> =
        appRepository.getAParticularVerse(chapter_num, verse_num)

    suspend fun deleteSavedVerse(chapter_num: Int, verse_num: Int) =
        appRepository.deleteSavedVerse(chapter_num, verse_num)


    //saved chapter in Shared pref
    fun getAllSavedChapterKeySP(): Set<String> = appRepository.getAllSavedChapterKeySP()

    fun putSavedChapterSP(key: String, value: Int) =
        appRepository.putSavedChapterSP(key, value)

    fun deleteSavedChapterSP(key: String) = appRepository.deleteSavedChapterSP(key)


    //saved verses in Shared pref---------------------------------------------------------------------------

    fun getAllSavedVersesKeySP(): Set<String> = appRepository.getAllSavedVersesKeySP()

    fun putSavedVersesSP(key: String, value: Int) =
        appRepository.putSavedVersesSP(key, value)

    fun deleteSavedVersesSP(key: String) = appRepository.deleteSavedVersesSP(key)

}