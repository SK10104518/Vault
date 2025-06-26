package com.example.vault.ui.auth

import android.util.Log
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.vault.R
import com.example.vault.SessionManager
import com.example.vault.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sessionManager = SessionManager(requireContext())
        if (sessionManager.isLoggedIn()) {
            findNavController().navigate(R.id.mainFragment)
            return // Stop further execution in this method
        }

        // Observer for the login status from the ViewModel
        authViewModel.loginStatus.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                // Login successful
                Log.d("LoginDebug", "Login SUCCESSFUL for user: ${user.username}")
                Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
                // Save user session
                sessionManager.saveSession(user.id)
                // Navigate to the main part of the app
                findNavController().navigate(R.id.mainFragment)
            } else {
                // Login failed
                Log.d("LoginDebug", "Login FAILED. The user object from ViewModel was null.")
                Toast.makeText(context, "Invalid username or password.", Toast.LENGTH_SHORT).show()
            }
        }

        // Set click listener for the login button
        binding.buttonLogin.setOnClickListener {
            Log.d("LoginDebug", "Login button CLICKED.")
            val username = binding.editTextUsername.text.toString().trim()
            val password = binding.editTextPassword.text.toString()
            authViewModel.login(username, password)
        }

        // Set click listener for the register button (changed from textViewRegisterLink)
        binding.buttonRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}