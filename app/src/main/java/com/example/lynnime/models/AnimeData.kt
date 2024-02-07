package com.example.lynnime.models

import com.google.gson.annotations.SerializedName

data class AnimeData(

    @SerializedName("_id") var Id: String? = null,
    @SerializedName("uid") var uid: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("synopsis") var synopsis: String? = null,
    @SerializedName("genre") var genre: String? = null,
    @SerializedName("aired") var aired: String? = null,
    @SerializedName("episodes") var episodes: Int? = null,
    @SerializedName("members") var members: Int? = null,
    @SerializedName("popularity") var popularity: Int? = null,
    @SerializedName("ranked") var ranked: Int? = null,
    @SerializedName("score") var score: Double? = null,
    @SerializedName("img_url") var imgUrl: String? = null,
    @SerializedName("link") var link: String? = null
)
