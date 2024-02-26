package com.example.lynnime.fragments

import JikanAnimeModel
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lynnime.R
import com.example.lynnime.api.RetrofitClient
import com.example.lynnime.databinding.FragmentHomeBinding
import com.example.lynnime.models.AnimeData
import com.example.lynnime.models.HorrorMovieData
import com.example.lynnime.utils.AnimeAdapter
import com.example.lynnime.utils.HorrorMovieAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var animeAdapter: AnimeAdapter
    private lateinit var upcomingAnimeAdapter: AnimeAdapter
    private lateinit var TopAnimeAdapter: AnimeAdapter

    private var animeList: MutableList<JikanAnimeModel.Anime> = mutableListOf()
    private var upcomingAnimeList: MutableList<JikanAnimeModel.Anime> = mutableListOf()
    private var topAnimeList: MutableList<JikanAnimeModel.Anime> = mutableListOf()

    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUser = Firebase.auth.currentUser
        updateUI(currentUser)

//        binding.btnSignOut.setOnClickListener {
//            Firebase.auth.signOut()
//            findNavController().navigate(R.id.onboardingFragment)
//        }

        initRecyclerView()
        initUpcomingRecyclerView()
        initTopRecyclerView()

        fetchAnimeData()
        fetchUpcomingAnimeData()
        fetchTopAnimeData()
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            // Set the TextView to the user's display name
            binding.tv1.text = "Welcome " + user.displayName
        } else {
            // Handle the case where the user is null (not signed in or signed out)
            binding.tv1.text = "Not signed in"
        }
    }

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

    private fun fetchUpcomingAnimeData() {
        RetrofitClient.instance.getUpcomingSeasonAnime().enqueue(object : retrofit2.Callback<JikanAnimeModel> {
            override fun onResponse(call: retrofit2.Call<JikanAnimeModel>, response: retrofit2.Response<JikanAnimeModel>) {
                if (response.isSuccessful && response.body() != null) {
                    upcomingAnimeList.clear()
                    upcomingAnimeList.addAll(response.body()!!.data)
                    upcomingAnimeAdapter.notifyDataSetChanged()
                } else {
                    Log.e("HomeFragment", "Error fetching upcoming anime data")
                }
            }

            override fun onFailure(call: retrofit2.Call<JikanAnimeModel>, t: Throwable) {
                Log.e("HomeFragment", "Failure: ${t.message}")
            }
        })
    }

    private fun fetchTopAnimeData() {
        RetrofitClient.instance.getTopAnime().enqueue(object : retrofit2.Callback<JikanAnimeModel> {
            override fun onResponse(call: retrofit2.Call<JikanAnimeModel>, response: retrofit2.Response<JikanAnimeModel>) {
                if (response.isSuccessful && response.body() != null) {
                    topAnimeList.clear()
                    topAnimeList.addAll(response.body()!!.data)
                    TopAnimeAdapter.notifyDataSetChanged()
                } else {
                    Log.e("HomeFragment", "Error fetching top anime data")
                }
            }

            override fun onFailure(call: retrofit2.Call<JikanAnimeModel>, t: Throwable) {
                Log.e("HomeFragment", "Failure: ${t.message}")
            }
        })
    }
    private fun initRecyclerView() {
        animeAdapter = AnimeAdapter(animeList, { anime ->
            if (isAdded) {
                val bundle = Bundle().apply {
                    putParcelable("animeData", anime) // Ensure Anime data class is Parcelable
                }
                findNavController().safeNavigate(R.id.action_homeFragment_to_movieDetailsFragment, bundle)
            }
        }, limitTitleLength = true, showRecentlyAddedLabel = true)

        binding.animeRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.animeRecyclerView.adapter = animeAdapter
    }

    private fun initUpcomingRecyclerView() {
        upcomingAnimeAdapter = AnimeAdapter(upcomingAnimeList, { anime ->
            // Similar onClick functionality
            if (isAdded) {
                val bundle = Bundle().apply {
                    putParcelable("animeData", anime)
                }
                findNavController().safeNavigate(R.id.action_homeFragment_to_movieDetailsFragment, bundle)
            }
        }, limitTitleLength = true, showPopularLabel = true)

        binding.animeRecyclerView2.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.animeRecyclerView2.adapter = upcomingAnimeAdapter
    }

    private fun initTopRecyclerView() {
        TopAnimeAdapter = AnimeAdapter(topAnimeList, { anime ->
            // Similar onClick functionality
            if (isAdded) {
                val bundle = Bundle().apply {
                    putParcelable("animeData", anime)
                }
                findNavController().safeNavigate(R.id.action_homeFragment_to_movieDetailsFragment, bundle)
            }
        }, limitTitleLength = true)

        binding.animeRecyclerView3.layoutManager = GridLayoutManager(context, 3)
        binding.animeRecyclerView3.adapter = TopAnimeAdapter
    }


    fun NavController.safeNavigate(destinationId: Int, bundle: Bundle? = null) {
        currentDestination?.let { currentDestination ->
            if (currentDestination.id != destinationId) {
                navigate(destinationId, bundle)
            }
        }
    }
}