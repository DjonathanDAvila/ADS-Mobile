package com.example.trabalho_final.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trabalho_final.dao.TravelDao

class HomeViewModelFactory(private val travelDao: TravelDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(travelDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
