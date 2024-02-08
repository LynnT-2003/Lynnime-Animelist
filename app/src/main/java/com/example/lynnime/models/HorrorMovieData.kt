package com.example.lynnime.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class HorrorMovieData(
    val id: Int? = null,
    val title: String? = null,
    val posterURL: String? = null,
    val imdbId: String? = null
) : Parcelable