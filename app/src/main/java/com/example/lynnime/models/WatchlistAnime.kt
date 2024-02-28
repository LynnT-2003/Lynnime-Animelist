package com.example.lynnime.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Watchlist(
    val name: String,
    val animeList: MutableList<WatchlistAnime>
) : Parcelable

@Parcelize
data class WatchlistAnime(
    val title: String? = null,
    val synopsis: String? = null,
    val imageUrl: String? = null
) : Parcelable