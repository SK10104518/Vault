package com.example.vault

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.vault.databinding.FragmentSettingsBinding
import com.example.vault.ui.auth.AuthViewModel

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by activityViewModels()
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        sessionManager = SessionManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Check if user is logged in
        val currentUserId = sessionManager.getLoggedInUserId()
        if (currentUserId == -1L) {
            Toast.makeText(context, "Please log in to access settings", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_settingsFragment_to_loginFragment)
            return
        }

        // Load current user data to prefill goal fields
        authViewModel.loadUser(currentUserId)
        authViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.editTextMinGoal.setText(it.monthlyGoalMin?.toString() ?: "")
                binding.editTextMaxGoal.setText(it.monthlyGoalMax?.toString() ?: "")
            }
        }

        // Set up save button for updating goals
        binding.buttonSaveGoals.setOnClickListener {
            saveGoals(currentUserId)
        }

        // Set up logout button
        binding.buttonLogout.setOnClickListener {
            sessionManager.logout()
            Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_settingsFragment_to_loginFragment)
        }
    }

    private fun saveGoals(userId: Long) {
        val minGoalStr = binding.editTextMinGoal.text.toString()
        val maxGoalStr = binding.editTextMaxGoal.text.toString()

        if (minGoalStr.isBlank() || maxGoalStr.isBlank()) {
            Toast.makeText(context, "Please enter both minimum and maximum goals", Toast.LENGTH_SHORT).show()
            return
        }

        val minGoal = minGoalStr.toDoubleOrNull()
        val maxGoal = maxGoalStr.toDoubleOrNull()

        if (minGoal == null || maxGoal == null || minGoal < 0 || maxGoal <= minGoal) {
            Toast.makeText(context, "Please enter valid goals (min > 0, max > min)", Toast.LENGTH_SHORT).show()
            return
        }

        authViewModel.updateUserGoals(userId, minGoal, maxGoal)
        Toast.makeText(context, "Goals updated successfully", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}