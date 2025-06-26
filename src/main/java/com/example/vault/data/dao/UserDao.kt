package com.example.vault.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.vault.data.entities.User


@Dao
interface UserDao {
    // Function to add a new user to the database.
    // The `suspend` keyword means this function must be called from a coroutine (background thread).
    @Insert
    suspend fun insertUser(user: User)

    // Function to find a user by their username.
    // This will be used for the login process.
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    suspend fun getUserById(userId: Long): User?
}