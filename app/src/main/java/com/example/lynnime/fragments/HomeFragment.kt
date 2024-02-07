package com.example.lynnime.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lynnime.R
import com.example.lynnime.api.RetrofitClient
import com.example.lynnime.databinding.FragmentHomeBinding
import com.example.lynnime.models.AnimeData
import com.example.lynnime.models.HorrorMovieData
import com.example.lynnime.utils.AnimeAdapter
import com.example.lynnime.utils.HorrorMovieAdapter


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var animeAdapter: AnimeAdapter
    private var animeList: MutableList<AnimeData> = mutableListOf()

    private lateinit var horrorMovieAdapter: HorrorMovieAdapter
    private var horrorMoviesList: MutableList<HorrorMovieData> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
//        getAnimeData()
        getHorrorMovies()
    }

//    private fun getAnimeData() {
//        RetrofitClient.instance.getAnimeList().enqueue(object : retrofit2.Callback<List<AnimeData>> {
//            override fun onResponse(call: retrofit2.Call<List<AnimeData>>, response: retrofit2.Response<List<AnimeData>>) {
//                if (response.isSuccessful) {
//                    animeList.clear()
//                    animeList.addAll(response.body()!!)
//                    Log.d("HomeFragment", "Fetched anime list size: ${animeList.size}")
//                    animeAdapter.notifyDataSetChanged()
//                } else {
//                    Log.e("HomeFragment", "Error fetching data")
//                }
//            }
//
//            override fun onFailure(call: retrofit2.Call<List<AnimeData>>, t: Throwable) {
//                Log.e("HomeFragment", "Failure: ${t.message}")
//            }
//        })
//    }

    private fun getHorrorMovies() {
        RetrofitClient.instance.getHorrorMovies().enqueue(object : retrofit2.Callback<List<HorrorMovieData>> {
            override fun onResponse(call: retrofit2.Call<List<HorrorMovieData>>, response: retrofit2.Response<List<HorrorMovieData>>) {
                if (response.isSuccessful) {
                    horrorMoviesList.clear()
                    horrorMoviesList.addAll(response.body()!!)
                    horrorMovieAdapter.notifyDataSetChanged()
                } else {
                    Log.e("HomeFragment", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<List<HorrorMovieData>>, t: Throwable) {
                Log.e("HomeFragment", "Failure: ${t.message}")
            }
        })
    }


    private fun initRecyclerView() {
//        animeAdapter = AnimeAdapter(animeList)
//        binding.animeRecyclerView.layoutManager = LinearLayoutManager(context)
//        binding.animeRecyclerView.adapter = animeAdapter

        horrorMovieAdapter = HorrorMovieAdapter(horrorMoviesList)
        binding.animeRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.animeRecyclerView.adapter = horrorMovieAdapter
    }
}