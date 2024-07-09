package com.example.nutri.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food")

class Food(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "unit")
    val servingUnit: ServingUnit,
    @ColumnInfo(name = "serving_size")
    val servingSize: Double,

//    macros to track
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

//    nutrition label / ingredients in a serialized display format
//    e.g. the whole nutrition label
    @ColumnInfo(name = "nutrition_facts")
    val nutritionFacts: String,

//    custom notes added by user (we would populate this with any ChatGPT notes)
    @ColumnInfo(name = "user_notes")
    val userNotes: String,

//    file path for now, we could also store a blob image
    @ColumnInfo(name = "thumbnail")
    val thumbnail: String,
    )

enum class ServingUnit(val value: Int) {
    GRAMS(0),
    LITERS(1),
    ML(2)
}