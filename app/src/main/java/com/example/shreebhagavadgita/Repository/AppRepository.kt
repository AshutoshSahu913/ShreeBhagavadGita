package com.example.shreebhagavadgita.Repository

import android.util.Log
import com.example.shreebhagavadgita.DataSource.API.ApiUtils
import com.example.shreebhagavadgita.DataSource.Models.Chapters
import com.example.shreebhagavadgita.DataSource.Models.VersesItem
import com.example.shreebhagavadgita.DataSource.Room.SaveChapterDao
import com.example.shreebhagavadgita.DataSource.Room.SavedChapterEntity
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppRepository(val saveChapterDao: SaveChapterDao) {

    fun getAllChapters(): Flow<Chapters> = callbackFlow {
        val callBack = object : Callback<Chapters> {
            override fun onResponse(call: Call<Chapters>, response: Response<Chapters>) {
                if (response.isSuccessful && response.body() != null) {
                    trySend(response.body()!!)
                    close()
                }
            }

            override fun onFailure(call: Call<Chapters>, t: Throwable) {
                close(t)
            }
        }
        ApiUtils.api.getAllChapters().enqueue(callBack)
        awaitClose {

        }
    }


    fun getVerses(chapterNumber: Int): Flow<List<VersesItem>> = callbackFlow {
        val callback = object : Callback<List<VersesItem>> {
            override fun onResponse(call: Call<List<VersesItem>>, response: Response<List<VersesItem>>) {
                if (response.isSuccessful && response.body() != null) {
                    Log.d("VERSES", "onResponse------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------: ${response.body()}")
                    trySend(response.body()!!)
                    close()
                }
            }

            override fun onFailure(call: Call<List<VersesItem>>, t: Throwable) {
                close(t)
            }
        }
        ApiUtils.api.getVerses(chapterNumber).enqueue(callback)
        awaitClose {

        }
    }

    fun getParticularVerse(chapterNumber: Int, verseNum: Int): Flow<VersesItem> = callbackFlow {
        val callback = object : Callback<VersesItem> {
            override fun onResponse(call: Call<VersesItem>, response: Response<VersesItem>) {
                if (response.isSuccessful && response.body() != null) {
                    Log.d(
                        "SINGLE_VERSE",
                        "onResponse------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------: ${response.body()}"
                    )
                    trySend(response.body()!!)
                    close()
                }
            }

            override fun onFailure(call: Call<VersesItem>, t: Throwable) {
                close(t)
            }
        }
        ApiUtils.api.getParticularVerse(chapterNumber, verseNum).enqueue(callback)
        awaitClose {

        }
    }


    suspend fun insertData(savedChapter: SavedChapterEntity) =
        saveChapterDao.insertData(savedChapter)
}