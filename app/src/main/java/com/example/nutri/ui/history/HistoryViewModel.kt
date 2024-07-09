package com.example.nutri.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutri.database.NutriDatabase
import com.example.nutri.database.dao.AteDao.DailyNutrients
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryViewModel(private val appDatabase: NutriDatabase) : ViewModel() {

    private val _dailyCalories = MutableLiveData<List<DailyNutrients>>()
    val dailyCalories: LiveData<List<DailyNutrients>> = _dailyCalories

    fun fetchDailyCalories(month: String) {
        viewModelScope.launch(Dispatchers.IO) {
            appDatabase.ateDao().getNutrientsConsumedPerDay(month).collect { dailyNutrients ->
                _dailyCalories.postValue(dailyNutrients)
            }
        }
    }
}