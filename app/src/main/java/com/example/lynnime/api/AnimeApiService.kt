package com.example.lynnime.api

import com.example.lynnime.models.AnimeData
import retrofit2.Call
import retrofit2.http.GET

interface AnimeApiService {
    @GET("anime3000")
    fun getAnimeList(): Call<List<AnimeData>>
}