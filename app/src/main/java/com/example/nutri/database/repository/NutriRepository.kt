package com.example.nutri.database.repository

import com.example.nutri.database.dao.UserDao
import com.example.nutri.database.entity.User

import androidx.annotation.WorkerThread
import com.example.nutri.database.NutriDatabase
import com.example.nutri.database.dao.AteDao
import com.example.nutri.database.dao.FoodDao
import com.example.nutri.database.dao.GoalDao
import com.example.nutri.database.entity.Ate
import kotlinx.coroutines.flow.Flow
import java.time.OffsetDateTime

class NutriRepository(
    private val nutriDatabase: NutriDatabase,
    ) {
    val allUsers: Flow<List<User>> = nutriDatabase.userDao().getAll()
    val allAteToday: Flow<List<Ate>> = nutriDatabase.ateDao().getAllOnDay(OffsetDateTime.now())

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(user: User) {
        nutriDatabase.userDao().insert(user)
    }
}