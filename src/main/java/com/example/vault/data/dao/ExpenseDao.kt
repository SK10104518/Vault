package com.example.vault.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.vault.data.CategorySpending
import com.example.vault.data.entities.Expense

@Dao
interface ExpenseDao {
    @Insert
    suspend fun insertExpense(expense: Expense)
    // Query to get all expenses for a specific user within a given date range.
    // :userId, :startDate, and :endDate are parameters we will pass to the function.
    @Query("""
        SELECT * FROM expenses
        WHERE userId = :userId AND date BETWEEN :startDate AND :endDate
        ORDER BY date DESC
    """)
    fun getExpensesForUser(userId: Long, startDate: Long, endDate: Long): LiveData<List<Expense>>

    // Query to get the total amount spent per category for a user in a specific date range.
    // It joins the expenses and categories tables and groups the results.
    @Query("""
        SELECT c.name as categoryName, SUM(e.amount) as totalAmount
        FROM expenses e
        JOIN categories c ON e.categoryId = c.id
        WHERE e.userId = :userId AND e.date BETWEEN :startDate AND :endDate
        GROUP BY c.name
        ORDER BY totalAmount DESC
    """)
    fun getCategorySpending(userId: Long, startDate: Long, endDate: Long): LiveData<List<CategorySpending>>
}