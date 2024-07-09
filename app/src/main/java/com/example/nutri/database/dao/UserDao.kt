package com.example.nutri.database.dao

import com.example.nutri.database.entity.User

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): Flow<List<User>>

    @Insert
    fun insert(user: User)

    @Query("DELETE FROM user")
    fun deleteAll()
}
