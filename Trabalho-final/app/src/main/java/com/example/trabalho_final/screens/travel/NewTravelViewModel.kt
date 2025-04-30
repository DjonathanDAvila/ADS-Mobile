package com.example.trabalho_final.screens.travel


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trabalho_final.dao.TravelDao
import com.example.trabalho_final.entity.Travel
import com.example.trabalho_final.entity.enums.TravelType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset

data class NewTravelUiState(
    val id: Int = 0,
    val destination: String = "",
    val type: TravelType = TravelType.LAZER,
    val startDate: LocalDateTime = LocalDateTime.now(),
    val endDate: LocalDateTime = LocalDateTime.now().plusDays(2),
    val budget: String = "",
    val showSuccessDialog: Boolean = false
)

class NewTravelViewModel(private val travelDao: TravelDao) : ViewModel() {
    private val _uiState = MutableStateFlow(NewTravelUiState())
    val uiState: StateFlow<NewTravelUiState> = _uiState

    fun onDestinoChange(value: String) {
        _uiState.value = _uiState.value.copy(destination = value)
    }

    fun onTipoChange(value: TravelType) {
        _uiState.value = _uiState.value.copy(type = value)
    }


    fun onDataInicioChange(timestamp: Long) {
        val dataFim = _uiState.value.endDate
        val minFim = LocalDateTime.ofEpochSecond(timestamp / 1000, 0, ZoneOffset.UTC).plusDays(2)
        val novoFim = if (dataFim.isBefore(minFim)) minFim else dataFim

        _uiState.value = _uiState.value.copy(
            startDate = LocalDateTime.ofEpochSecond(timestamp / 1000, 0, ZoneOffset.UTC),
            endDate = novoFim
        )
    }

    fun onDataFimChange(timestamp: Long) {
        _uiState.value = _uiState.value.copy(
            endDate = LocalDateTime.ofEpochSecond(timestamp / 1000, 0, ZoneOffset.UTC)
        )
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