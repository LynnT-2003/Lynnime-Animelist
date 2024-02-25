package com.example.lynnime.api

import JikanAnimeModel
import retrofit2.Call
import retrofit2.http.GET

interface JikanApiService {
    @GET("seasons/now")
    fun getCurrentSeasonAnime(): Call<JikanAnimeModel>

    @GET("top/anime")
    fun getTopAnime(): Call<JikanAnimeModel>

    @GET("seasons/upcoming")
    fun getUpcomingSeasonAnime(): Call<JikanAnimeModel>
}
