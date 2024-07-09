package com.example.nutri.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val _restrictions = MutableLiveData<List<String>>().apply {
        value = listOf("Peanuts", "Cashews", "Pineapples")
    }
    val restrictions: LiveData<List<String>> = _restrictions

    private val _height = MutableLiveData<Double>().apply {
        value = 180.0
    }
    val height: LiveData<Double> = _height

    private val _weight = MutableLiveData<Double>().apply {
        value = 86.0
    }
    val weight: LiveData<Double> = _weight

    private val _sex = MutableLiveData<String>().apply {
        value = "Male"
    }
    val sex: LiveData<String> = _sex

    // Update height
    fun setHeight(height: Double) {
        _height.value = height
    }

    // Update weight
    fun setWeight(weight: Double) {
        _weight.value = weight
    }

    // Update sex
    fun setSex(sex: String) {
        _sex.value = sex
    }

    // Add a restriction to the list
    fun addRestriction(restriction: String) {
        val updatedRestrictions = _restrictions.value?.toMutableList() ?: mutableListOf()
        updatedRestrictions.add(restriction)
        _restrictions.value = updatedRestrictions
    }

    // Remove a restriction from the list
    fun removeRestriction(restriction: String) {
        val updatedRestrictions = _restrictions.value?.toMutableList() ?: mutableListOf()
        updatedRestrictions.remove(restriction)
        _restrictions.value = updatedRestrictions
    }
}
