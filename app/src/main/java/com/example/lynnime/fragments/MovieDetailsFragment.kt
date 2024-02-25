package com.example.lynnime.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.addCallback
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.lynnime.R
import com.example.lynnime.models.HorrorMovieData

class MovieDetailsFragment : Fragment() {

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

//        val selectedMovie = arguments?.getParcelable<HorrorMovieData>("movieData")
        val selectedAnime = arguments?.getParcelable<JikanAnimeModel.Anime>("animeData")

        val posterImageView = view.findViewById<ImageView>(R.id.detailMoviePosterImageView)
        val titleTextView = view.findViewById<TextView>(R.id.detailMovieTitleTextView)
        val descriptionTextView = view.findViewById<TextView>(R.id.detailMovieDescriptionTextView)

        selectedAnime?.let { anime ->
            Glide.with(this).load(anime.images.jpg.largeImageUrl).into(posterImageView)
            titleTextView.text = anime.titleEnglish ?: "Title not Available"
            descriptionTextView.text = anime.synopsis
        }

        // Handle back press
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Handle the back button event
            navigateBackToHomeFragment()
        }
    }

    private fun navigateBackToHomeFragment() {
//        findNavController().navigate(R.id.homeFragment)
        navController.navigate(R.id.action_movieDetailsFragment_to_homeFragment )
    }

}