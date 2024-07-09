package com.example.nutri.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.nutri.database.dao.*
import com.example.nutri.database.entity.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStreamReader
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class Converters {
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    fun toOffsetDateTime(value: String?): OffsetDateTime? {
        return value?.let {
            return formatter.parse(value, OffsetDateTime::from)
        }
    }

    @TypeConverter
    fun fromOffsetDateTime(date: OffsetDateTime?): String? {
        return date?.format(formatter)
    }
}

@Database(entities = [Ate::class, Food::class, Goal::class, User::class], version = 1)
@TypeConverters(Converters::class)
abstract class NutriDatabase : RoomDatabase() {
    abstract fun ateDao(): AteDao
    abstract fun goalDao(): GoalDao
    abstract fun foodDao(): FoodDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: NutriDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): NutriDatabase {
            return INSTANCE ?: synchronized(this) {
                Log.d("NutriDatabase", "Initializing database...")
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NutriDatabase::class.java,
                    "nutri_database"
                )
                    .fallbackToDestructiveMigration()
                    .createFromAsset("database/data.db")
                    .addCallback(DatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                Log.d("NutriDatabase", "Database initialized")
                instance
            }
        }

        private class DatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Log.d("NutriDatabase", "Database created from asset")
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                Log.d("NutriDatabase", "Database opened")
            }
        }
    }
}
