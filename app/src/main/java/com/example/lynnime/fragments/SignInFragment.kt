package com.example.lynnime.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.lynnime.R
import com.example.lynnime.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {

    private lateinit var navControl: NavController
    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        registerEvents()
    }

    private fun init(view: View) {
        navControl = Navigation.findNavController(view)
    }

    private fun registerEvents() {
        binding.btnSignInToHome.setOnClickListener {
            navControl.navigate(R.id.action_signInFragment_to_homeFragment)
        }

        binding.textViewSignIn.setOnClickListener {
            navControl.navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }

}