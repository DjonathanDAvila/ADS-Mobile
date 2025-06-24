package com.example.trabalho_final.screens.Suggestion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trabalho_final.dao.SuggestionDAO

class SuggestionViewModelFactory(
    private val suggestionDao: SuggestionDAO,
    private val geminiRepository: GeminiRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SuggestionViewModel(suggestionDao, geminiRepository) as T
    }
}
