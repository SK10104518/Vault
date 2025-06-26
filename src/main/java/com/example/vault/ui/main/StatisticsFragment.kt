package com.example.vault.ui.main

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.vault.R
import com.example.vault.SessionManager
import com.example.vault.data.CategorySpending
import com.example.vault.databinding.FragmentStatisticsBinding
import com.example.vault.ui.auth.AuthViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.NumberFormat
import java.util.Locale

class StatisticsFragment : Fragment() {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        sessionManager = SessionManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textViewGoalStatus.text = "Statistics Fragment Loaded"
        binding.textViewGoalStatus.visibility = View.VISIBLE
        Log.d("StatisticsFragment", "Fragment loaded")

        // 1. Get the current user's ID and set it in the ViewModels
        val currentUserId = sessionManager.getLoggedInUserId()
        Log.d("StatisticsFragment", "User ID: $currentUserId")
        if (currentUserId == -1L){
            Log.d("StatisticsFragment", "No user logged in, exiting")
            return
        } // Exit if no user is logged in

        viewModel.setUserId(currentUserId)
        authViewModel.loadUser(currentUserId)

        // 2. Observe the current user's data (for goals)
        authViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            Log.d("StatisticsFragment", "Current User: $user")
            // 3. Inside the user observer, observe the category spending data
            viewModel.categorySpending.observe(viewLifecycleOwner) { spendingData ->
                Log.d("StatisticsFragment", "Spending Data: $spendingData")

                // 4. Perform calculations based on the latest data
                val totalSpent = spendingData?.sumOf { it.totalAmount } ?: 0.0
                updateGoalProgress(totalSpent, user?.monthlyGoalMax)

                // 5. Check gamification goals and play sound if needed
                if (user?.monthlyGoalMin != null && user.monthlyGoalMax != null) {
                    if (totalSpent >= user.monthlyGoalMin!! && totalSpent <= user.monthlyGoalMax!!) {
                        playSuccessSound()
                    }
                }

                // 6. Update the Bar Chart UI
                if (spendingData.isNullOrEmpty()) {
                    binding.barChart.clear()
                    binding.barChart.invalidate()
                } else {
                    setupBarChart(spendingData)
                }
            }
        }

        // 7. Set up the listener for the filter chips
        binding.chipGroupFilter.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.chipWeek -> viewModel.setFilter(FilterType.WEEK)
                R.id.chipMonth -> viewModel.setFilter(FilterType.MONTH)
                R.id.chipAllTime -> viewModel.setFilter(FilterType.ALL_TIME)
            }
        }
    }

    private fun setupBarChart(spendingData: List<CategorySpending>) {
        val entries = ArrayList<BarEntry>()
        val labels = ArrayList<String>()

        spendingData.forEachIndexed { index, data ->
            entries.add(BarEntry(index.toFloat(), data.totalAmount.toFloat()))
            labels.add(data.categoryName)
        }

        val dataSet = BarDataSet(entries, "Spending per Category")
        dataSet.colors = listOf(
            Color.rgb(104, 241, 175), Color.rgb(255, 208, 140),
            Color.rgb(140, 234, 255), Color.rgb(255, 140, 157)
        )
        dataSet.valueTextSize = 12f

        val barData = BarData(dataSet)
        binding.barChart.data = barData

        val xAxis = binding.barChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.setDrawGridLines(false)

        binding.barChart.description.isEnabled = false
        binding.barChart.axisRight.isEnabled = false
        binding.barChart.legend.isEnabled = false
        binding.barChart.animateY(1000)
        binding.barChart.invalidate()
    }

    private fun updateGoalProgress(totalSpent: Double, maxGoal: Double?) {
        // This checks if the goal-related views exist in the binding to avoid crashes
        if (_binding == null || binding.root.findViewById<View>(R.id.textViewGoalStatus) == null) {
            return
        }

        if (maxGoal == null || maxGoal <= 0) {
            binding.textViewGoalStatus.visibility = View.GONE
            binding.progressBarGoal.visibility = View.GONE
            return
        }

        binding.textViewGoalStatus.visibility = View.VISIBLE
        binding.progressBarGoal.visibility = View.VISIBLE

        val format = NumberFormat.getCurrencyInstance(Locale("en", "ZA"))
        binding.textViewGoalStatus.text = "${format.format(totalSpent)} / ${format.format(maxGoal)}"

        binding.progressBarGoal.max = maxGoal.toInt()
        binding.progressBarGoal.progress = totalSpent.toInt()
    }

    private fun playSuccessSound() {
        try {
            MediaPlayer.create(context, R.raw.success_chime).start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}