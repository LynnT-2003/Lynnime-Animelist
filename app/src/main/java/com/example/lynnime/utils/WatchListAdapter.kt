package com.example.lynnime.utils

import android.health.connect.datatypes.units.Length
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lynnime.R
import com.example.lynnime.models.WatchlistAnime

class WatchlistAdapter(
    private var watchlist: List<WatchlistAnime>,
    private val onClick: (WatchlistAnime) -> Unit,
    private var limitTitleLength: Boolean = false
) : RecyclerView.Adapter<WatchlistAdapter.WatchlistViewHolder>() {

    class WatchlistViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val posterImageView: ImageView = view.findViewById(R.id.moviePosterImageView)
        val titleTextView: TextView = view.findViewById(R.id.movieTitleTextView)
        // You can remove the labels if they are not needed
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchlistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_each_anime, parent, false)
        return WatchlistViewHolder(view)
    }

    override fun onBindViewHolder(holder: WatchlistViewHolder, position: Int) {
        val anime = watchlist[position]

        var title = anime.title ?: "Title not available"
        if (limitTitleLength && title.length > 20) {
            title = title.substring(0,17) + "..."
        }
        holder.titleTextView.text = title

        Glide.with(holder.posterImageView.context)
            .load(anime.imageUrl ?: "")
            .placeholder(R.drawable.bg_splash) // Placeholder image if the URL is null
            .into(holder.posterImageView)

        holder.itemView.setOnClickListener { onClick(anime) }
    }

    override fun getItemCount(): Int = watchlist.size

    fun updateData(newWatchlist: List<WatchlistAnime>) {
        watchlist = newWatchlist
        notifyDataSetChanged()
    }
}

