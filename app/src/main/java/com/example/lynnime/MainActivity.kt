package com.example.lynnime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.lynnime.databinding.ActivityMainBinding
import com.example.lynnime.fragments.ExploreFragment
import com.example.lynnime.fragments.HomeFragment
import com.example.lynnime.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val binding = ActivityMainBinding.inflate(layoutInflater);
//        setContentView(binding.root)
//
//        binding.bottomNavigation.setOnItemSelectedListener{
//            when (it.itemId) {
//                R.id.navigation_home -> {
//                    replaceFragment(HomeFragment())
//                    true
//                }
//                R.id.navigation_discover -> {
//                    replaceFragment(ExploreFragment())
//                    true
//                }
//                R.id.navigation_profile -> {
//                    replaceFragment(ProfileFragment())
//                    true
//                }
//
//                else -> {false}
//            }
//        }
//
//        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        val navController = navHostFragment.navController
//
//        NavigationUI.setupWithNavController(bottomNavigationView, navController)
//
//        navController.addOnDestinationChangedListener {_, destination, _ ->
//            when (destination.id) {
//                R.id.carouselFragment, R.id.onboardingFragment -> bottomNavigationView.visibility = View.GONE
//                else -> bottomNavigationView.visibility = View.VISIBLE
//            }
//        }
//    }
//
//    private fun replaceFragment(fragment: Fragment) {
//        val fragmentManager = supportFragmentManager
//        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.frame_layout, fragment)
//        fragmentTransaction.commit()
//    }
//
//}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    replaceFragment( HomeFragment())
                    true
                }
                R.id.navigation_discover -> {
                    replaceFragment(ExploreFragment())
                    true
                }
                R.id.navigation_profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}

