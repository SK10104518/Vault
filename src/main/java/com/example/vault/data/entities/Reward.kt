package com.example.vault.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rewards")
data class Reward(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: String,
    val name: String,
    val description: String,
    val imageUrl: String?, // Optional: Path to an image representing the badge
    val earnedDate: Long, // Timestamp when the reward was earned
    val type: String // e.g., "milestone", "spending_goal", "category_master"
)