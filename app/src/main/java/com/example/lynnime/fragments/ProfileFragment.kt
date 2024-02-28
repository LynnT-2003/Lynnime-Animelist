package com.example.lynnime.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lynnime.R
import com.example.lynnime.databinding.FragmentProfileBinding
import com.example.lynnime.models.AnimeData
import com.example.lynnime.models.WatchlistAnime
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

        initRecyclerView()
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

    private fun initRecyclerView() {
//        watchlistAdapter = WatchlistAdapter(watchlist) { anime ->
//            // Handle click events, such as navigating to a detail view
//        }

        watchlistAdapter = WatchlistAdapter(
            watchlist,
            { anime -> {}},
            limitTitleLength = true
        )

        binding.watchlistRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = watchlistAdapter
        }
    }

    private fun loadWatchlist() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()

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




    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            binding.userTv.text = user.displayName
        } else {
            binding.userTv.text = "User Not Available (Note to Self: Please don't get disappointed at this message"
        }
    }
}