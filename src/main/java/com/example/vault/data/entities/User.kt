package com.example.vault.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val username: String,
    val passwordHash: String, // Important: Store a hash, not the plain password
    val monthlyGoalMin: Double?,
    val monthlyGoalMax: Double?
)