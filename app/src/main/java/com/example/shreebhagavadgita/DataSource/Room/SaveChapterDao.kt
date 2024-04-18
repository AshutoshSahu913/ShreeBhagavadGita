package com.example.shreebhagavadgita.DataSource.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SaveChapterDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertData(savedChapter: SavedChapterEntity)

    @Query("SELECT * From SavedChapters")
    fun getSavedChapter(): LiveData<List<SavedChapterEntity>>

    @Query("DELETE FROM savedchapters where id = :id")
    fun deleteSavedChapter(id: Int)

    @Query("SELECT * FROM SavedChapters Where chapter_number = :chapter_num")
    fun getAParticularChapter(chapter_num: Int): LiveData<SavedChapterEntity>

}