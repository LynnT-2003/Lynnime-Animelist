package com.example.lynnime.utils

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lynnime.R
import com.example.lynnime.models.WatchlistAnime

class AnimeInWatchlistAdapter(
    private val AnimeList: List<WatchlistAnime>,
    private val onAnimeClick: (WatchlistAnime) -> Unit,
    private val limitTitleLength: Boolean = false,
) : RecyclerView.Adapter<AnimeInWatchlistAdapter.AnimeViewHolder>() {

    class AnimeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val posterImageView: ImageView = view.findViewById(R.id.moviePosterImageView)
        val titleTextView: TextView = view.findViewById(R.id.movieTitleTextView)
        // Initialize other views if necessary
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_each_anime, parent, false)
        return AnimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val anime = AnimeList[position]
        var title = anime.title ?: "Title not available"

        holder.titleTextView.text = anime.title ?: "Title not available"
        Glide.with(holder.posterImageView.context).load(anime.imageUrl).into(holder.posterImageView)
        holder.itemView.setOnClickListener { onAnimeClick(anime) }

        if (limitTitleLength && title.length > 20) {
            title = title.substring(0,17) + "..."
        }
        holder.titleTextView.text = title
    }

    override fun getItemCount(): Int {
        Log.d("AnimeAdapter", "List size: ${AnimeList.size}")
        return AnimeList.size
    }
}
