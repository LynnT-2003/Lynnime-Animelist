package com.example.lynnime.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lynnime.R
import com.example.lynnime.databinding.FragmentProfileBinding
import com.example.lynnime.models.AnimeData
import com.example.lynnime.models.Watchlist
import com.example.lynnime.models.WatchlistAnime
import com.example.lynnime.utils.AnimeInWatchlistAdapter
import com.example.lynnime.utils.WatchlistAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var watchlistAdapter: WatchlistAdapter
    private var watchlist: MutableList<WatchlistAnime> = mutableListOf()
    private var watchlists: MutableList<Watchlist> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_profile, container, false)
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUser = Firebase.auth.currentUser
        updateUI(currentUser)

        loadWatchlists()
        loadWatchlist()

        binding.signOutBtn.setOnClickListener {
            Firebase.auth.signOut()
            findNavController().navigate(R.id.onboardingFragment)
        }

        binding.createWatchlistBtn.setOnClickListener {
            // Trigger the watchlist creation process
            createNewWatchlist()
        }
    }

    private fun createNewWatchlist() {
        val editText = EditText(context)
        AlertDialog.Builder(context)
            .setTitle("Create New Watchlist")
            .setView(editText)
            .setPositiveButton("Create") { dialog, which ->
                val watchlistName = editText.text.toString()
                if (watchlistName.isNotEmpty()) {
                    addWatchlistToFirebase(watchlistName)
                } else {
                    Toast.makeText(context, "Watchlist name cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun addWatchlistToFirebase(watchlistName: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val watchlist = mapOf("name" to watchlistName)

        FirebaseFirestore.getInstance().collection("Users")
            .document(userId)
            .collection("Watchlists")
            .add(watchlist)
            .addOnSuccessListener {
                Toast.makeText(context, "Watchlist created successfully", Toast.LENGTH_SHORT).show()
                // Optionally, refresh the watchlist RecyclerView
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error creating watchlist: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

//    private fun initRecyclerView() {
////        watchlistAdapter = WatchlistAdapter(watchlist) { anime ->
////            // Handle click events, such as navigating to a detail view
////        }
//
//        watchlistAdapter = WatchlistAdapter(
//            watchlist,
//            { anime -> {}},
//            limitTitleLength = true
//        )
//
//        binding.watchlistRecyclerView.apply {
//            layoutManager = GridLayoutManager(context, 3)
//            adapter = watchlistAdapter
//        }
//    }

    private fun loadWatchlist() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()
        watchlistAdapter = WatchlistAdapter(
            watchlist,
            { anime -> {}},
            limitTitleLength = true
        )

        userId?.let { uid ->
            db.collection("Users").document(uid).collection("Watchlist")
                .get()
                .addOnSuccessListener { documents ->
                    val fetchedWatchlist = documents.mapNotNull { document ->
                        document.toObject(WatchlistAnime::class.java)
                    }
                    watchlist.clear()
                    watchlist.addAll(fetchedWatchlist)
                    watchlistAdapter.notifyDataSetChanged()
                }
                .addOnFailureListener { exception ->
                    Log.w("ProfileFragment", "Error getting documents: ", exception)
                }
        }
    }

    private fun loadWatchlists() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()

        db.collection("Users").document(userId).collection("Watchlists")
            .get()
            .addOnSuccessListener { watchlistsSnapshot ->
                watchlistsSnapshot.forEach { watchlistDocument ->
                    val watchlistName = watchlistDocument.getString("name") ?: "Unnamed"
                    val animeList = mutableListOf<WatchlistAnime>()

                    // Now fetch the AnimeList for the current watchlist
                    db.collection("Users").document(userId)
                        .collection("Watchlists").document(watchlistDocument.id)
                        .collection("AnimeList")
                        .get()
                        .addOnSuccessListener { animeListSnapshot ->
                            animeListSnapshot.forEach { animeDocument ->
                                val anime = animeDocument.toObject(WatchlistAnime::class.java)
                                animeList.add(anime)
                            }
                            // Create the watchlist object with the fetched anime list
                            val watchlist = Watchlist(name = watchlistName, animeList = animeList)
                            // Add to the list of watchlists
                            watchlists.add(watchlist)

                            // After fetching all watchlists and their anime lists, dynamically create views for each
                            activity?.runOnUiThread {
                                populateWatchlists(watchlists)
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.w("ProfileFragment", "Error getting anime list for watchlist: $watchlistName", exception)
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("ProfileFragment", "Error getting watchlists: ", exception)
            }
    }

//    private fun populateWatchlists(watchlists: List<Watchlist>) {
//        val container = binding.watchlistContainer
//        container.removeAllViews() // Clear previous views if any
//
//        watchlists.forEach { watchlist ->
//            // Inflate a layout for the watchlist section
//            val watchlistView = layoutInflater.inflate(R.layout.watchlist_section, container, false)
//            val watchlistTitle = watchlistView.findViewById<TextView>(R.id.watchlistTitle)
//            val watchlistRecyclerView = watchlistView.findViewById<RecyclerView>(R.id.watchlistRecyclerView)
//
//            // Set the title for the watchlist
//            watchlistTitle.text = watchlist.name
//
//            // Set up the RecyclerView
//            val adapter = watchlist.animeList?.let {
//                AnimeInWatchlistAdapter(it) { anime ->
//                    // TODO: Implement onClick for anime items, e.g., navigate to anime details
//                }
//            }
//            watchlistRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//            watchlistRecyclerView.adapter = adapter
//
//            // Add the watchlist view to the container
//            container.addView(watchlistView)
//        }
//    }

    private fun populateWatchlists(watchlists: List<Watchlist>) {
        val container = binding.watchlistContainer
        container.removeAllViews() // Clear previous views if any

        watchlists.forEach { watchlist ->
            // Inflate a layout for the watchlist section
            val watchlistView = layoutInflater.inflate(R.layout.watchlist_section, container, false)
            val watchlistTitle = watchlistView.findViewById<TextView>(R.id.watchlistTitle)
            val watchlistRecyclerView =
                watchlistView.findViewById<RecyclerView>(R.id.watchlistRecyclerView)

            // Set the title for the watchlist
            watchlistTitle.text = watchlist.name

            // Set up the RecyclerView\
            if (!watchlist.animeList.isNullOrEmpty()) {
                val animeAdapter = AnimeInWatchlistAdapter(watchlist.animeList, { anime ->
                    // TODO: Implement onClick for anime items, e.g., navigate to anime details
                }, limitTitleLength = true) // Set to true to limit the title length

                watchlistRecyclerView.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                watchlistRecyclerView.adapter = animeAdapter
            }

            // Add the watchlist view to the container
            container.addView(watchlistView)
        }
    }








    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            binding.userTv.text = user.displayName
        } else {
            binding.userTv.text = "User Not Available (Note to Self: Please don't get disappointed at this message"
        }
    }
}
