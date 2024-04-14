package com.example.shreebhagavadgita.DataSource.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiUtils {

    private var baseUrl = "https://bhagavad-gita3.p.rapidapi.com/"
    val api: ApiService by lazy {
        Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}