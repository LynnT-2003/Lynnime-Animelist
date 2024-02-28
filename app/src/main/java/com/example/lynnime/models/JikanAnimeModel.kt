import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class JikanAnimeModel(
    @SerializedName("data") val data: List<Anime>
) : Parcelable {
    @Parcelize
    data class Anime(
        @SerializedName("mal_id") val malId: Int = 0,
        @SerializedName("title_english") val titleEnglish: String? = null,
        @SerializedName("score") val score: Double = 0.0,
        @SerializedName("rank") val rank: Int = 0,
        @SerializedName("popularity") val popularity: Int = 0,
        @SerializedName("synopsis") val synopsis: String = "",
        @SerializedName("genres") val genres: List<Genre> = emptyList(),
        @SerializedName("images") val images: AnimeImages = AnimeImages(AnimeImages.AnimeImageDetails()),
        @SerializedName("trailer") val trailer: AnimeTrailer? = null
    ) : Parcelable {
        @Parcelize
        data class Genre(
            @SerializedName("name") val name: String = ""
        ) : Parcelable

        @Parcelize
        data class AnimeImages(
            @SerializedName("jpg") val jpg: AnimeImageDetails = AnimeImageDetails()
        ) : Parcelable {
            @Parcelize
            data class AnimeImageDetails(
                @SerializedName("large_image_url") val largeImageUrl: String? = null
            ) : Parcelable
        }

        @Parcelize
        data class AnimeTrailer(
            @SerializedName("url") val url: String? = null
        ) : Parcelable
    }
}
