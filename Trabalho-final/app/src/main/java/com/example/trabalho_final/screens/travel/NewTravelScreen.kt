package com.example.trabalho_final.screens.travel

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trabalho_final.database.AppDataBase
import com.example.trabalho_final.entity.enums.TravelType
import java.text.SimpleDateFormat
import java.time.ZoneOffset
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun NewTravelScreen(
    travelId: Int?,
    onNavigateTo: (String) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val travelDao = AppDataBase.getDatabase(context).travelDao()
    val viewModel: NewTravelViewModel = viewModel(factory = NewTravelViewModelFactory(travelDao))
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(travelId) {
        if (travelId != null) {
            viewModel.loadTravel(travelId)
        }
    }

    val formatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    val showDatePicker = { current: Long, minDate: Long, onDateSelected: (Long) -> Unit ->
        val calendar = Calendar.getInstance().apply { timeInMillis = current }
        val dialog = DatePickerDialog(
            context,
            { _, year, month, day ->
                val selected = Calendar.getInstance()
                selected.set(year, month, day)
                onDateSelected(selected.timeInMillis)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dialog.datePicker.minDate = minDate
        dialog.show()
    }


    if (state.showSuccessDialog) {
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {
                TextButton(onClick = {
                    viewModel.dismissSuccessDialog()
                    onBack()
                }) {
                    Text("OK")
                }
            },
            title = { Text("Viagem salva!") },
            text = { Text("Sua viagem foi registrada com sucesso.") }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Nova Viagem", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            label = { Text("Destino") },
            value = state.destination,
            onValueChange = { viewModel.onDestinoChange(it) },
            modifier = Modifier.fillMaxWidth()
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = state.type == TravelType.NEGOCIO,
                onClick = { viewModel.onTipoChange(TravelType.NEGOCIO) }
            )
            Text("Negócio")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = state.type == TravelType.LAZER,
                onClick = { viewModel.onTipoChange(TravelType.LAZER) }
            )
            Text("Lazer")
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    val startDateMillis =
                        state.startDate.atZone(ZoneOffset.systemDefault()).toInstant()
                            .toEpochMilli()
                    showDatePicker(
                        startDateMillis,
                        System.currentTimeMillis(),
                        viewModel::onDataInicioChange
                    )
                }
        ) {
            OutlinedTextField(
                value = formatter.format(
                    Date(
                        state.startDate.atZone(ZoneOffset.systemDefault()).toInstant()
                            .toEpochMilli()
                    )
                ),
                onValueChange = {},
                label = { Text("Data de Início") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )
        }


        val minEndDate = Calendar.getInstance().apply {
            val startDateMillis =
                state.startDate.atZone(ZoneOffset.systemDefault()).toInstant()
                    .toEpochMilli()
            timeInMillis = startDateMillis
            add(Calendar.DAY_OF_MONTH, 2)
        }.timeInMillis

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    val startDateMillis =
                        state.startDate.atZone(ZoneOffset.systemDefault()).toInstant()
                            .toEpochMilli()
                    showDatePicker(startDateMillis, minEndDate, viewModel::onDataFimChange)
                }
        ) {
            OutlinedTextField(
                value = formatter.format(Date(state.endDate.atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli())),
                onValueChange = {},
                label = { Text("Data de Fim") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )
        }



        OutlinedTextField(
            label = { Text("Orçamento") },
            value = state.budget,
            onValueChange = { viewModel.onOrcamentoChange(it) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (travelId != null) {
                    viewModel.updateTravel(context) { }
                } else {
                    viewModel.saveTravel(context) { }
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Salvar")
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun PreviewNewTravel() {
    MaterialTheme {
        NewTravelScreen(travelId = 0, onNavigateTo = {}, onBack = {})
    }
}