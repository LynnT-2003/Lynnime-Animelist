package com.example.lynnime.api

import JikanAnimeModel
import retrofit2.Call
import retrofit2.http.GET

interface JikanApiService {
    @GET("seasons/now")
    fun getCurrentSeasonAnime(): Call<JikanAnimeModel>
}
