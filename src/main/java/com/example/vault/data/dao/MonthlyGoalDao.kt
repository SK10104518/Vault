package com.example.vault.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.vault.data.entities.MonthlyGoal
import kotlinx.coroutines.flow.Flow

@Dao
interface MonthlyGoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMonthlyGoal(goal: MonthlyGoal)

    @Update
    suspend fun updateMonthlyGoal(goal: MonthlyGoal)

    @Query("SELECT * FROM monthly_goals WHERE userId = :userId AND monthYear = :monthYear")
    suspend fun getMonthlyGoalForMonth(userId: String, monthYear: String): MonthlyGoal?

    @Query("SELECT * FROM monthly_goals WHERE userId = :userId ORDER BY monthYear DESC")
    fun getAllMonthlyGoalsForUser(userId: String): Flow<List<MonthlyGoal>>
}