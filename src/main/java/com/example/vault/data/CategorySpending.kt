package com.example.vault.data

// This is not an entity, just a class to hold query results.
data class CategorySpending(
    val categoryName: String,
    val totalAmount: Double
)