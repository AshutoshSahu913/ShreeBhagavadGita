package com.example.shreebhagavadgita.DataSource.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SaveChapterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(savedChapter: SavedChapterEntity)

    @Query("SELECT * From SavedChapters")
    fun getSavedChapter(): LiveData<List<SavedChapterEntity>>

    @Query("DELETE FROM savedchapters where id = :id")
  suspend  fun deleteSavedChapter(id: Int)

    @Query("SELECT * FROM SavedChapters Where chapter_number = :chapter_num")
    fun getAParticularChapter(chapter_num: Int): LiveData<SavedChapterEntity>

}

@Dao
interface SavedVersesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEnglishVerse(savedVerses: SavedVersesEntity)

    @Query("SELECT * From SavedVerses")
    fun getAllEnglishVerse(): LiveData<List<SavedVersesEntity>>

    @Query("SELECT * FROM SavedVerses Where chapter_number = :chapter_num AND verse_number= :verse_num")
    fun getAParticularVerse(chapter_num: Int, verse_num: Int): LiveData<SavedVersesEntity>

    @Query("DELETE FROM SavedVerses where chapter_number = :chapter_num AND verse_number= :verse_num")
    suspend fun deleteSavedVerse(chapter_num: Int, verse_num: Int)

}