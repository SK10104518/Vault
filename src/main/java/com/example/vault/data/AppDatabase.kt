package com.example.vault.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.vault.data.entities.Category
import com.example.vault.data.entities.Expense
import com.example.vault.data.entities.User
import com.example.vault.data.dao.CategoryDao
import com.example.vault.data.dao.ExpenseDao
import com.example.vault.data.dao.UserDao


@Database(
    entities = [User::class, Category::class, Expense::class],
    version = 1, // Increment this if you change the schema in the future
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // Abstract functions that will return our DAOs
    abstract fun userDao(): UserDao
    abstract fun categoryDao(): CategoryDao
    abstract fun expenseDao(): ExpenseDao

    // The companion object allows us to define static members, including the singleton instance.
    companion object {
        // @Volatile ensures that the INSTANCE variable is always up-to-date and the same for all execution threads.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // This function gets the singleton instance of the database.
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "vault_database"
                )
                    // You can add migrations here if you change the schema later
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}