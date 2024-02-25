package com.example.lynnime.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.lynnime.R
import com.example.lynnime.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

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

        binding.signOutBtn.setOnClickListener {
            Firebase.auth.signOut()
            findNavController().navigate(R.id.onboardingFragment)
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