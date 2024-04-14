package com.example.shreebhagavadgita.Repository

import com.example.shreebhagavadgita.DataSource.API.ApiUtils
import com.example.shreebhagavadgita.DataSource.Models.Chapters
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppRepository {

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

}