package com.example.vault.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vault.data.entities.Reward
import kotlinx.coroutines.flow.Flow

@Dao
interface RewardDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE) // Ignore if reward already exists
    suspend fun insertReward(reward: Reward)

    @Query("SELECT * FROM rewards WHERE userId = :userId ORDER BY earnedDate DESC")
    fun getRewardsForUser(userId: String): Flow<List<Reward>>

    @Query("SELECT COUNT(*) FROM rewards WHERE userId = :userId AND name = :rewardName")
    suspend fun getRewardCountByName(userId: String, rewardName: String): Int
}