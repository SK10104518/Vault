package com.example.vault // Use your actual package name

import android.app.Application
import com.example.vault.data.AppDatabase
import com.example.vault.data.entities.Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VaultApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // This is the perfect place for one-time setup
        setupDefaultCategories()
    }

    private fun setupDefaultCategories() {
        // We need to do this in a background thread so we don't block the UI
        CoroutineScope(Dispatchers.IO).launch {
            val categoryDao = AppDatabase.getDatabase(applicationContext).categoryDao()
            if (categoryDao.getCategoryCount() == 0) {
                // If the table is empty, add our defaults
                categoryDao.insertCategory(Category(name = "Food"))
                categoryDao.insertCategory(Category(name = "Transport"))
                categoryDao.insertCategory(Category(name = "Bills"))
                categoryDao.insertCategory(Category(name = "Shopping"))
                categoryDao.insertCategory(Category(name = "Entertainment"))
                categoryDao.insertCategory(Category(name = "Other"))
            }
        }
    }
}