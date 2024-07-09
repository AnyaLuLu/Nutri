package com.example.nutri.ui.addItem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddItemViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Choose how you will add the items"
    }
    val text: LiveData<String> = _text



}