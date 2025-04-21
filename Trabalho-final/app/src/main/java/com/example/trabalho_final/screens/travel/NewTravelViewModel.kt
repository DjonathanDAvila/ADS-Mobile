package com.example.trabalho_final.screens.travel


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trabalho_final.dao.TravelDao
import com.example.trabalho_final.entity.Travel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NewTravelUiState(
    val id: Int = 0,
    val destination: String = "",
    val type: String = "Lazer",
    val startDate: Long = System.currentTimeMillis(),
    val endDate: Long = System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000,
    val budget: String = "",
    val showSuccessDialog: Boolean = false
)

class NewTravelViewModel(private val travelDao: TravelDao) : ViewModel() {
    private val _uiState = MutableStateFlow(NewTravelUiState())
    val uiState: StateFlow<NewTravelUiState> = _uiState

    fun onDestinoChange(value: String) {
        _uiState.value = _uiState.value.copy(destination = value)
    }

    fun onTipoChange(value: String) {
        _uiState.value = _uiState.value.copy(type = value)
    }

    fun onDataInicioChange(timestamp: Long) {
        val dataFim = _uiState.value.endDate
        val minFim = timestamp + 2 * 24 * 60 * 60 * 1000
        val novoFim = if (dataFim < minFim) minFim else dataFim

        _uiState.value = _uiState.value.copy(
            startDate = timestamp,
            endDate = novoFim
        )
    }

    fun onDataFimChange(timestamp: Long) {
        _uiState.value = _uiState.value.copy(endDate = timestamp)
    }

    fun onOrcamentoChange(value: String) {
        _uiState.value = _uiState.value.copy(budget = value)
    }

    fun saveTravel(context: Context, onSaved: () -> Unit) {
        viewModelScope.launch {
            val state = _uiState.value
            val orcamentoDouble = state.budget.toDoubleOrNull() ?: 0.0

            val travel = Travel(
                destination = state.destination,
                type = state.type,
                startDate = state.startDate,
                endDate = state.endDate,
                budget = orcamentoDouble
            )

            travelDao.insertTravel(travel)
            _uiState.value = state.copy(showSuccessDialog = true)
            onSaved()
        }
    }

    fun updateTravel(context: Context, onUpdated: () -> Unit) {
        viewModelScope.launch {
            val state = _uiState.value
            val orcamentoDouble = state.budget.toDoubleOrNull() ?: 0.0

            // Supondo que o ID da viagem seja algo que você já tenha no estado, por exemplo:
            val travel = Travel(
                id = state.id,
                destination = state.destination,
                type = state.type,
                startDate = state.startDate,
                endDate = state.endDate,
                budget = orcamentoDouble
            )

            // Atualiza a viagem no banco
            travelDao.updateTravel(travel)

            // Atualiza o estado para mostrar o diálogo de sucesso
            _uiState.value = state.copy(showSuccessDialog = true)

            // Chama a função de callback para indicar que a atualização foi feita
            onUpdated()
        }
    }

    fun dismissSuccessDialog() {
        _uiState.update { it.copy(showSuccessDialog = false) }
    }

    fun loadTravel(id: Int) {
        viewModelScope.launch {
            val travel = travelDao.getTravelById(id)
            travel?.let {
                _uiState.update { state ->
                    state.copy(
                        id = it.id,
                        destination = it.destination,
                        type = it.type,
                        startDate = it.startDate,
                        endDate = it.endDate,
                        budget = it.budget.toString()
                    )
                }
            }
        }
    }
}