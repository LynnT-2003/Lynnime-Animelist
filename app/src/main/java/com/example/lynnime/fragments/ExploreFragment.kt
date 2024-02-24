package com.example.lynnime.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.lynnime.R
import com.example.lynnime.databinding.FragmentExploreBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class ExploreFragment : Fragment() {

    private lateinit var binding: FragmentExploreBinding
    private lateinit var navController: NavController

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
        super.onViewCreated(view, savedInstanceState)
//        navController = Navigation.findNavController(view)

//        val bottomNav = view.findViewById<BottomNavigationView>(R.id.bottom_navigation)
//        // Set
////        bottomNav.selectedItemId = R.id.navigation_discover
//        bottomNav.setOnNavigationItemReselectedListener { item ->
//            when (item.itemId) {
//                R.id.navigation_discover -> {
//                    findNavController().navigate(R.id.action_global_exploreFragment)
//                }
//            }
//            when (item.itemId) {
//                R.id.navigation_profile -> {
//                    findNavController().navigate(R.id.action_global_profileFragment)
//                }
//            }
//        }
    }
}