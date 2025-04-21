package com.example.trabalho_final.screens.travel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trabalho_final.dao.TravelDao

class NewTravelViewModelFactory(
    private val travelDao: TravelDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewTravelViewModel(travelDao) as T
    }
}