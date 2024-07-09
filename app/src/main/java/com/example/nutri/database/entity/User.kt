package com.example.nutri.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class User(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "age")
    val age: Int,
    @ColumnInfo(name = "weight")
    val weight: Double,
    @ColumnInfo(name = "height")
    val height: Double,
    @ColumnInfo(name = "sex")
    val sex: Sex,
    @ColumnInfo(name = "allergies")
    val allergies: String?,
    @ColumnInfo(name = "restrictions")
    val restrictions: String?,
)

enum class Sex(val value: Int) {
    FEMALE(0),
    MALE(1)
}
