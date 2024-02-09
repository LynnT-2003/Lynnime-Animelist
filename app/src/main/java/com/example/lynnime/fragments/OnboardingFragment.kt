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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class OnboardingFragment : Fragment() {

    private lateinit var navControl: NavController
    private lateinit var binding: FragmentOnboardingBinding

    // GAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("816051741160-ldep1jfcoe47eqanbqfd4m3ruvg62akn.apps.googleusercontent.com")
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        navControl = Navigation.findNavController(view)

        checkCurrentUser()
    }

    private fun checkCurrentUser() {
        val currentUser = auth.currentUser
        if (currentUser != null) {

            Handler(Looper.myLooper()!!).postDelayed(Runnable {
//            navControl.navigate(R.id.action_onboardingFragment_to_signInFragment)
                navControl.navigate(R.id.action_onboardingFragment_to_homeFragment)
            }, 2000)
        } else {
            // User is not signed in, stay on the onboarding screen or navigate to sign-in screen


            Handler(Looper.myLooper()!!).postDelayed(Runnable {
//            navControl.navigate(R.id.action_onboardingFragment_to_signInFragment)
                navControl.navigate(R.id.action_onboardingFragment_to_carouselFragment)
            }, 2000)
        }
    }
}