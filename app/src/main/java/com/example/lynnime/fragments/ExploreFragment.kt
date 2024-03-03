package com.example.lynnime.fragments

import JikanAnimeModel
import android.app.AlertDialog
import android.graphics.Movie
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.lynnime.R
import com.example.lynnime.api.RetrofitClient
import com.example.lynnime.databinding.FragmentExploreBinding
import com.example.lynnime.models.HorrorMovieData
import com.example.lynnime.utils.AnimeAdapter
import com.example.lynnime.utils.HorrorMovieAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ExploreFragment : Fragment() {

    private lateinit var binding: FragmentExploreBinding
    private lateinit var animeAdapter: AnimeAdapter
    private var animeList: MutableList<JikanAnimeModel.Anime> = mutableListOf()

    private lateinit var navController: NavController

    private lateinit var horrorMovieAdapter: HorrorMovieAdapter
//    private var horrorMoviesList: MutableList<HorrorMovieData> = mutableListOf()

    private var currentIndex = 0;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_explore, container, false)
        binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        horrorMovieAdapter = HorrorMovieAdapter(horrorMoviesList) {}
        animeAdapter = AnimeAdapter(animeList, {anime ->
            val bundle = Bundle().apply {
                putParcelable("animeData", anime) // Ensure Anime data class is Parcelable
            }
            findNavController().navigate(R.id.action_exploreFragment_to_movieDetailsFragment, bundle)}, limitTitleLength = true)

        super.onViewCreated(view, savedInstanceState)
//        fetchAnimeData()
        fetchCombinedAnimeData()

        if (animeList.isNotEmpty()) {
            currentIndex = (currentIndex + 1) % animeList.size
            updateUI(animeList[currentIndex])
        }

        // If btnShowDetails is meant to show details of a specific item, set its OnClickListener
        binding.btnDetails.setOnClickListener {
            animeList.getOrNull(currentIndex)?.let { anime ->
                val bundle = Bundle().apply {
                    putParcelable("animeData", anime)
                }
                findNavController().navigate(R.id.action_exploreFragment_to_movieDetailsFragment, bundle)
            }
        }

        binding.btnAdd.setOnClickListener {
            animeList.getOrNull(currentIndex)?.let { anime ->
                showWatchlistSelectionDialog(anime)
            }
        }

        view.setOnClickListener {
            if (animeList.isNotEmpty()) {
                currentIndex = (currentIndex + 1) % animeList.size
                updateUI(animeList[currentIndex])
            }
        }
//        getHorrorMovies()
//        view.setOnClickListener {
//            if (horrorMoviesList.isNotEmpty()) {
//                currentIndex = (currentIndex + 1) % horrorMoviesList.size
//                updateUI(horrorMoviesList[currentIndex])
//            }
//        }
    }

//    private fun getHorrorMovies() {
//        RetrofitClient.instance.getHorrorMovies().enqueue(object : retrofit2.Callback<List<HorrorMovieData>> {
//            override fun onResponse(call: retrofit2.Call<List<HorrorMovieData>>, response: retrofit2.Response<List<HorrorMovieData>>) {
//                if (response.isSuccessful) {
//                    horrorMoviesList.clear()
//                    horrorMoviesList.addAll(response.body()!!)
//                    horrorMovieAdapter.notifyDataSetChanged()
//                } else {
//                    Log.e("HomeFragment", "Error: ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: retrofit2.Call<List<HorrorMovieData>>, t: Throwable) {
//                Log.e("HomeFragment", "Failure: ${t.message}")
//            }
//        })
//    }

    private fun fetchAnimeData() {
        RetrofitClient.instance.getCurrentSeasonAnime().enqueue(object : retrofit2.Callback<JikanAnimeModel> { // Use the correct callback type
            override fun onResponse(call: retrofit2.Call<JikanAnimeModel>, response: retrofit2.Response<JikanAnimeModel>) {
                if (response.isSuccessful && response.body() != null) {
                    animeList.clear()
                    animeList.addAll(response.body()!!.data) // Ensure your API's response structure is correctly navigated
                    animeAdapter.notifyDataSetChanged()
                } else {
                    Log.e("HomeFragment", "Error fetching anime data")
                }
            }

            override fun onFailure(call: retrofit2.Call<JikanAnimeModel>, t: Throwable) {
                Log.e("HomeFragment", "Failure: ${t.message}")
            }
        })
    }



    private fun fetchCombinedAnimeData() {
        val calls = listOf(
            RetrofitClient.instance.getCurrentSeasonAnime(),
            RetrofitClient.instance.getTopAnime(),
            RetrofitClient.instance.getUpcomingSeasonAnime()
        )

        calls.forEach { call ->
            call.enqueue(object : retrofit2.Callback<JikanAnimeModel> {
                override fun onResponse(call: retrofit2.Call<JikanAnimeModel>, response: retrofit2.Response<JikanAnimeModel>) {
                    if (response.isSuccessful && response.body() != null) {
                        animeList.addAll(response.body()!!.data)
                        if (animeList.size == calls.size * response.body()!!.data.size) {
                            animeList.shuffle()
                            animeAdapter.notifyDataSetChanged()
                        }
                    } else {
                        Log.e("ExploreFragment", "Error fetching anime data")
                    }
                }

                override fun onFailure(call: retrofit2.Call<JikanAnimeModel>, t: Throwable) {
                    Log.e("ExploreFragment", "Failure: ${t.message}")
                }
            })
        }
    }



    private fun showWatchlistSelectionDialog(currentAnime: JikanAnimeModel.Anime) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        FirebaseFirestore.getInstance().collection("Users").document(userId).collection("Watchlists")
            .get()
            .addOnSuccessListener { documents ->
                val watchlistNames = documents.map { it.getString("name") ?: "" }.toTypedArray()
                var selectedWatchlistName: String? = null
                AlertDialog.Builder(requireContext())
                    .setTitle("Select Watchlist")
                    .setSingleChoiceItems(watchlistNames, -1) { dialog, which ->
                        selectedWatchlistName = watchlistNames[which]
                    }
                    .setPositiveButton("Add") { dialog, which ->
                        selectedWatchlistName?.let { addToWatchlist(it, currentAnime) }
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
    }

    private fun addToWatchlist(watchlistName: String, anime: JikanAnimeModel.Anime) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val animeToAdd = hashMapOf(
            "title" to anime.titleEnglish,
            "synopsis" to anime.synopsis,
            "imageUrl" to anime.images.jpg.largeImageUrl
        )

        FirebaseFirestore.getInstance().collection("Users").document(userId)
            .collection("Watchlists").whereEqualTo("name", watchlistName)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Toast.makeText(context, "Watchlist not found", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }
                val watchlistId = documents.documents.first().id
                FirebaseFirestore.getInstance().collection("Users").document(userId)
                    .collection("Watchlists").document(watchlistId)
                    .collection("AnimeList")
                    .add(animeToAdd)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Anime added to watchlist", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, "Error adding anime to watchlist: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
    }

//    fun updateUI(movie: HorrorMovieData) {
//        binding.title.text = movie.title
////        binding.title2.text = movie.description
//        // Load image using Glide or similar library
//        Glide.with(this).load(movie.posterURL).into(binding.cover)
//        // Update tags, etc.
//    }

    private fun limitCharacters(text: String, maxChars: Int): String {
        if (text != null) {
            return if (text.length <= maxChars) text else text.take(maxChars) + "..."
        }
        return "(Description Not Available)"
    }

    private fun updateUI(anime: JikanAnimeModel.Anime) {
        binding.title.text = limitCharacters(anime.titleEnglish ?: "", 27)
        binding.title2.text = limitCharacters(anime.synopsis, 200)
        Glide.with(this).load(anime.images.jpg.largeImageUrl).into(binding.cover)
    }

}