package com.example.tip_calculator.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class TipCalculator(
    val amount: Float = 0.0f,
    val default: Float = 15f,
    val custom: Float = 18f,
    val tip: Float = 0.0f,
    val tipCustom: Float = 0.0f,
    val total: Float = 0.0f,
    val totalCustom: Float = 0.0f
)

class TipCalculatorViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TipCalculator())
    val uiState: StateFlow<TipCalculator> = _uiState.asStateFlow()

    fun onAmountChange(value: String) {
        try {
            val formatValue = value.replace("$", "")
            _uiState.value = _uiState.value.copy(amount = formatValue.toFloat())
        } catch (e: Exception) {
            Log.d("Tip calculator, ", e.message?: "Unk now")
        }
    }

    fun onDefaultChange(value: String) {
        _uiState.value = _uiState.value.copy(default = value.toFloat())
    }
}