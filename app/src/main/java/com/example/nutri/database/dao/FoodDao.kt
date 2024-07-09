package com.example.nutri.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nutri.database.entity.Food
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Query("SELECT * FROM food")
    fun getAll(): Flow<List<Food>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(food: Food)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(foods: List<Food>)

    @Query("DELETE FROM food")
    fun deleteAll(): Int

    @Query("SELECT COUNT(*) FROM food")
    fun getCount(): Int
}
