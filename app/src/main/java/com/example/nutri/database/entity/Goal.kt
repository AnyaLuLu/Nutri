package com.example.nutri.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "goal")
class Goal(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "date")
    val date: OffsetDateTime,

    @ColumnInfo(name = "calories")
    val calories: Double,
    @ColumnInfo(name = "fat")
    val fat: Double,
    @ColumnInfo(name = "sugar")
    val sugar: Double,
    @ColumnInfo(name = "sodium")
    val sodium: Double,
    @ColumnInfo(name = "protein")
    val protein: Double,
)
