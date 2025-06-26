package com.example.vault.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "monthly_goals")
data class MonthlyGoal(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: String,
    val monthYear: String, // e.g., "2024-03" for March 2024
    val minSpent: Double,
    val maxSpent: Double
)