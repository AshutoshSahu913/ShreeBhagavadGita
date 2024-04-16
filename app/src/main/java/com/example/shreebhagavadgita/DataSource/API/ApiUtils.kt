package com.example.shreebhagavadgita.DataSource.API

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiUtils {


    //use interceptor concept
    private val headers = mapOf<String, String>(
        "Accept" to "Application/json",
        "X-RapidAPI-Key" to "017136529emsh0e2a6165ea00ffcp1e3341jsne96214e382e0",
        "X-RapidAPI-Host" to "bhagavad-gita3.p.rapidapi.com"
    )

    private var client: OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor { chain ->
            val newRequest = chain.request().newBuilder().apply {
                headers.forEach { (key, value) ->
                    addHeader(key, value)
                }
            }.build()
            chain.proceed(newRequest)
        }
    }.build()

    private const val BASE_URL = "https://bhagavad-gita3.p.rapidapi.com/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}