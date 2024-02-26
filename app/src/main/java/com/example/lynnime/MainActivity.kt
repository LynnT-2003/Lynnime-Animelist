package com.example.lynnime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.lynnime.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            // Example of conditional UI update
            when (destination.id) {
                R.id.onboardingFragment, R.id.carouselFragment, R.id.signInFragment -> binding.bottomNavigation.visibility = View.GONE
                else -> binding.bottomNavigation.visibility = View.VISIBLE
            }
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
//                    replaceFragment( HomeFragment())
                    navController.navigate(R.id.action_global_homeFragment)
                    true
                }
                R.id.navigation_discover -> {
//                    replaceFragment(ExploreFragment())
                    navController.navigate(R.id.action_global_exploreFragment)
                    true
                }
                R.id.navigation_profile -> {
//                    replaceFragment(ProfileFragment())
                    navController.navigate(R.id.action_global_profileFragment)
                    true
                }
                else -> false
            }
        }
    }
}

