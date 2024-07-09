package com.example.nutri.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.nutri.database.entity.Goal
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {
    @Query("SELECT * FROM goal")
    fun getAll(): Flow<List<Goal>>

    @Insert
    fun insert(goal: Goal)

    @Query("DELETE FROM goal")
    fun deleteAll()
}
