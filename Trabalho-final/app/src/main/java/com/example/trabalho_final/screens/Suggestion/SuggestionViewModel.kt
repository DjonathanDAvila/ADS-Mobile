package com.example.trabalho_final.screens.Suggestion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trabalho_final.dao.SuggestionDAO
import com.example.trabalho_final.entity.Suggestion
import com.example.trabalho_final.entity.Travel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SuggestionViewModel(
    private val suggestionDao: SuggestionDAO,
    private val geminiRepository: GeminiRepository
) : ViewModel() {

    private val _suggestion = MutableStateFlow<Suggestion?>(null)
    val suggestion: StateFlow<Suggestion?> = _suggestion

    val isLoading = MutableStateFlow(false)

    fun loadSuggestion(travelId: Int) {
        viewModelScope.launch {
            _suggestion.value = suggestionDao.getByTravelId(travelId)
        }
    }

    fun generateAndSaveSuggestion(travel: Travel) {
        viewModelScope.launch {
//            _isLoading.value = true
            val result = geminiRepository.generateItinerary(travel)
            suggestionDao.insert(Suggestion(travelId = travel.id, text = result))
            _suggestion.value = Suggestion(travelId = travel.id, text = result)
//            _isLoading.value = false
        }
    }

    fun saveSuggestion(suggestion: Suggestion) {
        viewModelScope.launch {
            suggestionDao.insert(suggestion)
            _suggestion.value = suggestion
        }
    }
}
