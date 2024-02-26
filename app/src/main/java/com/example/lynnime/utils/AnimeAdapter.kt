//package com.example.lynnime.utils
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.lynnime.R
//import com.example.lynnime.models.AnimeData
//
//class AnimeAdapter(private val animeList: List<AnimeData>) : RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {
//    class AnimeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val imageView: ImageView = view.findViewById(R.id.animeImageView)
//        val titleTextView: TextView = view.findViewById(R.id.animeTitleTextView)
//        val synopsisTextView: TextView = view.findViewById(R.id.animeSynopsisTextView)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
//        val binding = LayoutInflater.from(parent.context).inflate(R.layout.item_anime, parent, false)
//        return AnimeViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: AnimeAdapter.AnimeViewHolder, position: Int) {
//        val anime = animeList[position]
//        holder.titleTextView.text = anime.title
//        holder.synopsisTextView.text = anime.synopsis
//        Glide.with(holder.imageView.context)
//            . load(anime.imgUrl)
//            . into(holder.imageView)
//    }
//
//    override fun getItemCount() = animeList.size
//}

package com.example.lynnime.utils

import JikanAnimeModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lynnime.R

class AnimeAdapter(
    private val animeList: List<JikanAnimeModel.Anime>, // Update to use the Jikan model
    private val onAnimeClick: (JikanAnimeModel.Anime) -> Unit,
    private val limitTitleLength: Boolean = false,
    private val showRecentlyAddedLabel: Boolean = false,
    private val showPopularLabel: Boolean = false,
    private val showAllTimeBestLabel: Boolean = false
) : RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {

    class AnimeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.movieTitleTextView)
        val posterImageView: ImageView = view.findViewById(R.id.moviePosterImageView)
        val recentlyAddedLabelView: TextView = view.findViewById(R.id.recentlyAddedLabel)
        val popularLabel: TextView = view.findViewById(R.id.popularLabel)
        val allTimeBestLabel: TextView = view.findViewById(R.id.allTimeBestLabel)
//        val scoreTextView: TextView = view.findViewById(R.id.animeScoreTextView)
        // Add more views if needed
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_horror_movie, parent, false)
        return AnimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val anime = animeList[position]

        var title = anime.titleEnglish ?: "Title not available"
        if (limitTitleLength && title.length > 20) {
            title = title.substring(0,17) + "..."
        }
        holder.titleTextView.text = title

        if (position < 3 && showRecentlyAddedLabel) {
            holder.recentlyAddedLabelView.visibility = View.VISIBLE
        } else {
            holder.recentlyAddedLabelView.visibility = View.GONE
        }

        if (position < 5 && showPopularLabel) {
            holder.popularLabel.visibility = View.VISIBLE
        } else {
            holder.popularLabel.visibility = View.GONE
        }

        if (position < 5 && showAllTimeBestLabel) {
            holder.allTimeBestLabel.visibility = View.VISIBLE
        } else {
            holder.allTimeBestLabel.visibility = View.GONE
        }

        val imageUrl = anime.images.jpg.largeImageUrl ?: ""
        Glide.with(holder.posterImageView.context)
            .load(imageUrl)
            .into(holder.posterImageView)

        holder.itemView.setOnClickListener { onAnimeClick(anime) }
    }

    override fun getItemCount(): Int = animeList.size
}
