package com.example.nutri.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.nutri.database.entity.Ate
import com.example.nutri.database.entity.User
import kotlinx.coroutines.flow.Flow
import java.time.OffsetDateTime


@Dao
interface AteDao {
    @Query("SELECT * FROM ate")
    fun getAll(): Flow<List<Ate>>

    @Query("SELECT * FROM ate WHERE date(time) = :queryDate")
    fun getAllOnDay(queryDate: String): Flow<List<Ate>>

    fun getAllOnDay(date: OffsetDateTime): Flow<List<Ate>> {
        return getAllOnDay(date.toLocalDate().toString())
    }
    @Insert
    fun insert(ate: Ate)

    @Query("DELETE FROM ate")
    fun deleteAll()

    @Query("""
        SELECT 
            date(a.time) as day,
            SUM(f.calories * a.num_servings) as total_calories,
            SUM(f.fat * a.num_servings) as total_fat,
            SUM(f.sugar * a.num_servings) as total_sugar,
            SUM(f.sodium * a.num_servings) as total_sodium,
            SUM(f.protein * a.num_servings) as total_protein
        FROM 
            ate a
        JOIN 
            food f ON a.food_id = f.id
        WHERE 
            strftime('%Y-%m', a.time) = :queryMonth
        GROUP BY 
            day
    """)
    fun getNutrientsConsumedPerDay(queryMonth: String): Flow<List<DailyNutrients>>

    data class DailyNutrients(
        val day: String,
        val total_calories: Double,
        val total_fat: Double,
        val total_sugar: Double,
        val total_sodium: Double,
        val total_protein: Double
    )
}
