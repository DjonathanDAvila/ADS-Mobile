package com.example.trabalho_final.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trabalho_final.dao.TravelDao
import com.example.trabalho_final.entity.Travel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val travelDao: TravelDao) : ViewModel() {

    private val _travels = MutableStateFlow<List<Travel>>(emptyList())
    val travels: StateFlow<List<Travel>> = _travels

    init {
        loadTravels()
    }

    private fun loadTravels() {
        viewModelScope.launch {
            _travels.value = travelDao.getAllTravels()
        }
    }

    fun deleteTravel(travel: Travel) {
        viewModelScope.launch {
            travelDao.deleteTravel(travel)
            loadTravels() // atualiza lista
        }
    }
}