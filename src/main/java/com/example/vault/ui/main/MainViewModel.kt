package com.example.vault.ui.main // Use your actual package name

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.vault.data.AppDatabase
import com.example.vault.data.CategoryRepository
import com.example.vault.data.ExpenseRepository
import com.example.vault.data.entities.Category
import com.example.vault.data.entities.Expense
import com.example.vault.data.CategorySpending
import kotlinx.coroutines.launch
import java.util.Calendar

class MainViewModel(application: Application) : AndroidViewModel(application) {

    // These properties hold references to our repositories
    private val expenseRepository: ExpenseRepository
    private val categoryRepository: CategoryRepository

    // LiveData for the reactive filtering system
    private val userId = MutableLiveData<Long>()
    private val filterType = MutableLiveData(FilterType.MONTH)

    // This init block runs when the ViewModel is created.
    // It sets up the database and repositories.
    init {
        val database = AppDatabase.getDatabase(application)
        expenseRepository = ExpenseRepository(database.expenseDao())
        categoryRepository = CategoryRepository(database.categoryDao())
    }

    val allCategories: LiveData<List<Category>> = categoryRepository.allCategories

    // THIS 'categorySpending' PROPERTY REPLACES THE OLD getCategorySpending() FUNCTION
    // The Fragment will observe this property.
    val categorySpending: LiveData<List<CategorySpending>> = userId.switchMap { id ->
        filterType.switchMap { filter ->

            val (start, end) = calculateDateRange(filter)
            // Notice this calls the repository, which calls the DAO
            expenseRepository.getCategorySpending(id, start, end)
        }
    }

    // This 'expenses' property is for the main expense list screen
    val expenses: LiveData<List<Expense>> = userId.switchMap { id ->
        filterType.switchMap { filter ->
            val (start, end) = calculateDateRange(filter)
            expenseRepository.getExpenses(id, start, end)
        }
    }

    // Public functions for Fragments to call
    fun setUserId(id: Long) {
        if (userId.value != id) {
            userId.value = id
        }
    }

    fun setFilter(filter: FilterType) {
        filterType.value = filter
    }

    private fun calculateDateRange(filter: FilterType): Pair<Long, Long> {
        val cal = Calendar.getInstance()
        return when (filter) {
            FilterType.WEEK -> {
                cal.set(Calendar.DAY_OF_WEEK, cal.firstDayOfWeek)
                cal.set(Calendar.HOUR_OF_DAY, 0)
                cal.set(Calendar.MINUTE, 0)
                val startDate = cal.timeInMillis
                cal.add(Calendar.WEEK_OF_YEAR, 1)
                val endDate = cal.timeInMillis
                Pair(startDate, endDate)
            }
            FilterType.MONTH -> {
                cal.set(Calendar.DAY_OF_MONTH, 1)
                cal.set(Calendar.HOUR_OF_DAY, 0)
                cal.set(Calendar.MINUTE, 0)
                val startDate = cal.timeInMillis
                cal.add(Calendar.MONTH, 1)
                val endDate = cal.timeInMillis
                Pair(startDate, endDate)
            }
            FilterType.ALL_TIME -> {
                Pair(0L, Long.MAX_VALUE)
            }
        }
    }

    fun insertExpense(expense: Expense) = viewModelScope.launch {
        expenseRepository.insert(expense)
    }

    fun insertCategory(category: Category) = viewModelScope.launch {
        categoryRepository.insert(category)
    }


}