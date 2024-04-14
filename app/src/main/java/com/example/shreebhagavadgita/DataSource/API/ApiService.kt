package com.example.shreebhagavadgita.DataSource.API

import com.example.shreebhagavadgita.DataSource.Models.Chapters
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {

    @Headers(
        "Accept: Application/json",
        "X-RapidAPI-Key: 017136529emsh0e2a6165ea00ffcp1e3341jsne96214e382e0",
        "X-RapidAPI-Host: bhagavad-gita3.p.rapidapi.com"
    )
    @GET("v2/chapters/")
    fun getAllChapters(): Call<Chapters>
}