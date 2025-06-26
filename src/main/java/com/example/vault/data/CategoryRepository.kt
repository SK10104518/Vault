package com.example.vault.data

import androidx.lifecycle.LiveData
import com.example.vault.data.dao.CategoryDao
import com.example.vault.data.entities.Category


class CategoryRepository(private val categoryDao: CategoryDao) {
    val allCategories: LiveData<List<Category>> = categoryDao.getAllCategories()
    suspend fun insert(category: Category) {
        categoryDao.insertCategory(category)
    }
}