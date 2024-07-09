package com.example.nutri

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.nutri.database.NutriDatabase
import com.example.nutri.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appDatabase: NutriDatabase

    fun getBinding() : ActivityMainBinding {
        return binding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_history, R.id.navigation_addItem, R.id.navigation_goals, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Initialize the database
        appDatabase = NutriDatabase.getDatabase(this, lifecycleScope)

        // Debug: Log database contents
        logDatabaseContents()
    }

    private fun logDatabaseContents() {
        val month = "2024-06"  // Example month

        lifecycleScope.launch(Dispatchers.IO) {
            val dailyNutrientsList = appDatabase.ateDao().getNutrientsConsumedPerDay(month)
            withContext(Dispatchers.Main) {
                dailyNutrientsList.collect { dailyNutrients ->
                    dailyNutrients?.let {
                        for (dailyNutrient in it) {
                            val formattedDate = dailyNutrient.day.split("-").let { dateParts ->
                                "${dateParts[1]}-${dateParts[2]}-${dateParts[0]}"
                            }
                            Log.d("Logging-Info", "$formattedDate: Calories: ${dailyNutrient.total_calories}, Fat: ${dailyNutrient.total_fat}, Sugar: ${dailyNutrient.total_sugar}, Sodium: ${dailyNutrient.total_sodium}, Protein: ${dailyNutrient.total_protein}")
                        }
                    }
                }
            }

            val foodCount = appDatabase.foodDao().getCount()
            Log.d("Logging-Info", "Food table contains: $foodCount items")
        }
    }
}
