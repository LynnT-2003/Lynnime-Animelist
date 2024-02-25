import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class JikanAnimeModel(
    @SerializedName("data") val data: List<Anime>
) : Parcelable {
    @Parcelize
    data class Anime(
        @SerializedName("mal_id") val malId: Int,
        @SerializedName("title_english") val titleEnglish: String?,
        @SerializedName("score") val score: Double,
        @SerializedName("rank") val rank: Int,
        @SerializedName("popularity") val popularity: Int,
        @SerializedName("synopsis") val synopsis: String,
        @SerializedName("genres") val genres: List<Genre>,
        @SerializedName("images") val images: AnimeImages,
        @SerializedName("trailer") val trailer: AnimeTrailer?
    ) : Parcelable {
        @Parcelize
        data class Genre(
            @SerializedName("name") val name: String
        ) : Parcelable

        @Parcelize
        data class AnimeImages(
            @SerializedName("jpg") val jpg: AnimeImageDetails
        ) : Parcelable {
            @Parcelize
            data class AnimeImageDetails(
                @SerializedName("large_image_url") val largeImageUrl: String?
            ) : Parcelable
        }

        @Parcelize
        data class AnimeTrailer(
            @SerializedName("url") val url: String?
        ) : Parcelable
    }
}
