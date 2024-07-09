package com.example.nutri.ui.goals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GoalsViewModel : ViewModel() {

    private val _calories = MutableLiveData<Double>().apply {
        value = 180.0
    }
    val calories: LiveData<Double> = _calories

    private val _sugar = MutableLiveData<Double>().apply {
        value = 86.0
    }
    val sugar: LiveData<Double> = _sugar

    private val _sodium = MutableLiveData<Double>().apply {
        value = 300.0
    }
    val sodium: LiveData<Double> = _sodium

    private val _fat = MutableLiveData<Double>().apply {
        value = 45.0
    }
    val fat: LiveData<Double> = _fat

    private val _protein = MutableLiveData<Double>().apply {
        value = 60.0
    }
    val protein: LiveData<Double> = _protein

    fun setCalories(value: Double) {
        _calories.value = value
    }

    fun setSugar(value: Double) {
        _sugar.value = value
    }

    fun setSodium(value: Double) {
        _sodium.value = value
    }

    fun setFat(value: Double) {
        _fat.value = value
    }

    fun setProtein(value: Double) {
        _protein.value = value
    }
}