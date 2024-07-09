package com.example.nutri.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.nutri.database.entity.Ate
import com.example.nutri.database.entity.User
import com.example.nutri.database.repository.NutriRepository
import com.example.nutri.ui.user.UserViewModel

class HomeViewModel(private val repository: NutriRepository) : ViewModel() {
    val allAteToday: LiveData<List<Ate>> = repository.allAteToday.asLiveData()

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}

class HomeViewModelFactory(private val repository: NutriRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("zzzzUnknown ViewModel class")
    }
}