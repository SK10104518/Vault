package com.example.vault.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vault.R
import com.example.vault.SessionManager
import com.example.vault.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var expenseAdapter: ExpenseAdapter
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        sessionManager = SessionManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        // 1. Get the current user's ID and set it in the ViewModel
        val currentUserId = sessionManager.getLoggedInUserId()
        if (currentUserId == -1L) return // Exit if no user is logged in

        viewModel.setUserId(currentUserId)

        // 2. Observe the public 'expenses' LiveData property from the ViewModel
        viewModel.expenses.observe(viewLifecycleOwner) { expenses ->
            // The list will automatically update when the filter changes
            expenseAdapter.submitList(expenses)
        }

        // 3. Set up listeners for UI elements
        binding.fabAddExpense.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addExpenseFragment)
        }

        binding.chipGroupFilter.setOnCheckedChangeListener { group, checkedId ->
            val filter = when (checkedId) {
                R.id.chipWeek -> FilterType.WEEK
                R.id.chipMonth -> FilterType.MONTH
                R.id.chipAllTime -> FilterType.ALL_TIME
                else -> null // Handle case where nothing is selected
            }
            filter?.let { viewModel.setFilter(it) }
        }
    }

    private fun setupRecyclerView() {
        expenseAdapter = ExpenseAdapter()
        binding.recyclerViewExpenses.apply {
            adapter = expenseAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}