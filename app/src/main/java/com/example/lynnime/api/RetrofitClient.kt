package com.example.lynnime.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    //    private const val BASE_URL = "https://lynnime-web02.vercel.app/api/"
    //    private const val BASE_URL = "https://lynnime-web02.vercel.app/api/"
    private const val BASE_URL = "https://api.jikan.moe/v4/"

//    val instance: MoviesApiService by lazy {
//        val retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        retrofit.create(MoviesApiService::class.java)
//    }

    val instance: JikanApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(JikanApiService::class.java)
    }

}