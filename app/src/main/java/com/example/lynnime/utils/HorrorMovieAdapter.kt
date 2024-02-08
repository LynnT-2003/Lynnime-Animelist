package com.example.lynnime.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lynnime.R
import com.example.lynnime.models.HorrorMovieData

class HorrorMovieAdapter(
    private val horrorMoviesList: List<HorrorMovieData>,
    private val onClick: (HorrorMovieData) -> Unit
) : RecyclerView.Adapter<HorrorMovieAdapter.HorrorMovieViewHolder>() {

    class HorrorMovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.movieTitleTextView)
        val posterImageView: ImageView = view.findViewById(R.id.moviePosterImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorrorMovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_horror_movie, parent, false)
        return HorrorMovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: HorrorMovieViewHolder, position: Int) {
        val movie = horrorMoviesList[position]
        holder.titleTextView.text = movie.title
        // Use Glide or another library to load the movie poster image
        Glide.with(holder.posterImageView.context)
            .load(movie.posterURL)
            .into(holder.posterImageView)

        // Set the onClick listener
        holder.itemView.setOnClickListener {
            onClick(movie) // Invoke the click listener, passing the clicked movie
        }
    }

    override fun getItemCount(): Int = horrorMoviesList.size
}
