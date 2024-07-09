package com.example.nutri.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "ate",
    foreignKeys = [ForeignKey(
    entity = Food::class,
    parentColumns = ["id"],
    childColumns = ["food_id"],
    onDelete = ForeignKey.RESTRICT)],
    indices = [Index("food_id")])

class Ate(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "food_id")
    val foodId: Int,
    @ColumnInfo(name = "time")
    val time: OffsetDateTime,
    @ColumnInfo(name = "num_servings")
    val numServings: Double
)
