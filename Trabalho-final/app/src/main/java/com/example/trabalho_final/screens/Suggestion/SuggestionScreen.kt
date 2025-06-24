package com.example.trabalho_final.screens.Suggestion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trabalho_final.dao.SuggestionDAO
import com.example.trabalho_final.dao.TravelDao
import com.example.trabalho_final.entity.Suggestion
import com.example.trabalho_final.entity.Travel


@Composable
fun SuggestionScreen(
    travelId: Int,
    travelDao: TravelDao,
    suggestionDao: SuggestionDAO,
    onBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var travel by remember { mutableStateOf<Travel?>(null) }

    val geminiRepository = remember { GeminiRepository() }
    val viewModel: SuggestionViewModel =
        viewModel(factory = SuggestionViewModelFactory(suggestionDao, geminiRepository))

    val suggestion by viewModel.suggestion.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var text by remember { mutableStateOf("") }

    LaunchedEffect(travelId) {
        travel = travelDao.getTravelById(travelId)
        viewModel.loadSuggestion(travelId)
    }

    LaunchedEffect(suggestion) {
        suggestion?.let { text = it.text }
    }

    travel?.let { travel ->
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Roteiro para: ${travel.destination}", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))

            if (isLoading) {
                CircularProgressIndicator()
            } else {
                if (suggestion == null) {
                    Button(onClick = {
                        viewModel.generateAndSaveSuggestion(travel)
                    }) {
                        Text("Gerar roteiro com IA")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                } else {
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        minLines = 8
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        viewModel.saveSuggestion(
                            Suggestion(travelId = travel.id, text = text, modified = true)
                        )
                        onBack()
                    }) {
                        Text("Salvar alterações")
                    }
                }
            }
        }
    }
}


@Composable
@Preview(showSystemUi = true, showBackground = true)
fun PreviewSuggestionScreen() {
    MaterialTheme {
        SuggestionScreen(0, TODO(), TODO(), TODO())
    }
}
