package com.example.lynnime.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.lynnime.R
import com.example.lynnime.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {

    private lateinit var navControl: NavController
    private lateinit var binding: FragmentOnboardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navControl = Navigation.findNavController(view)

        Handler(Looper.myLooper()!!).postDelayed(Runnable {
//            navControl.navigate(R.id.action_onboardingFragment_to_signInFragment)
            navControl.navigate(R.id.action_onboardingFragment_to_carouselFragment)
        }, 2000)
    }
}