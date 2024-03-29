package com.example.lynnime.api

import com.example.lynnime.models.HorrorMovieData
import retrofit2.Call
import retrofit2.http.GET

interface MoviesApiService {
    @GET("animation")
    fun getHorrorMovies(): Call<List<HorrorMovieData>>
}