package com.example.shreebhagavadgita.DataSource.API

import com.example.shreebhagavadgita.DataSource.Models.Chapters
import com.example.shreebhagavadgita.DataSource.Models.VersesItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("v2/chapters/")
    fun getAllChapters(): Call<Chapters>

    @GET("v2/chapters/{chapter_number}/verses/")
    fun getVerses(@Path("chapter_number") chapter_number: Int): Call<List<VersesItem>>

    @GET("v2/chapters/{chapterNum}/verses/{verseNum}/")
    fun getParticularVerse(
        @Path("chapterNum") chapterNumber: Int,
        @Path("verseNum") verseNumber: Int
    ): Call<VersesItem>


}