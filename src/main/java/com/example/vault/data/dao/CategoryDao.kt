package com.example.vault.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.vault.data.entities.Category


@Dao
interface CategoryDao {
    @Insert
    suspend fun insertCategory(category: Category)

    // Using LiveData here means the UI can automatically update whenever the list of categories changes.
    @Query("SELECT * FROM categories ORDER BY name ASC")
    fun getAllCategories(): LiveData<List<Category>>

    @Query("SELECT COUNT(id) FROM categories")
    suspend fun getCategoryCount(): Int
}