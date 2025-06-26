package com.example.vault.data

import androidx.lifecycle.LiveData
import com.example.vault.data.dao.ExpenseDao
import com.example.vault.data.entities.Expense


class ExpenseRepository(private val expenseDao: ExpenseDao) {
    fun getExpenses(userId: Long, startDate: Long, endDate: Long): LiveData<List<Expense>> {
        return expenseDao.getExpensesForUser(userId, startDate, endDate)
    }
    fun getCategorySpending(userId: Long, start: Long, end: Long): LiveData<List<CategorySpending>> {
        return expenseDao.getCategorySpending(userId, start, end)
    }
    suspend fun insert(expense: Expense) {
        expenseDao.insertExpense(expense)
    }
}