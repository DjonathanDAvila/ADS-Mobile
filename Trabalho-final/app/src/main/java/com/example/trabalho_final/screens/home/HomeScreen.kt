@file:Suppress("UNREACHABLE_CODE")

package com.example.trabalho_final.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trabalho_final.components.TravelCard
import com.example.trabalho_final.dao.TravelDao
import com.example.trabalho_final.entity.Travel

@Composable
fun HomeScreen(
    onEditTravel: (Int) -> Unit,
    travelDao: TravelDao
) {
    val viewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(travelDao)
    )
    val travels by viewModel.travels.collectAsState(initial = emptyList())
    var travelToDelete by remember { mutableStateOf<Travel?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
//        Text("Minhas Viagens", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(travels.sortedBy { it.startDate }) { travel ->
                TravelCard(
                    travel = travel,
                    onClick = { onEditTravel(travel.id) },
                    onLongClick = { travelToDelete = travel }
                )
            }
        }
    }

    // Diálogo de confirmação para excluir
    travelToDelete?.let { travel ->
        AlertDialog(
            onDismissRequest = { travelToDelete = null },
            title = { Text("Confirmar exclusão") },
            text = { Text("Deseja realmente excluir esta viagem?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteTravel(travel)
                    travelToDelete = null
                }) {
                    Text("Sim")
                }
            },
            dismissButton = {
                TextButton(onClick = { travelToDelete = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}


@Composable
@Preview(showSystemUi = true, showBackground = true)
fun PreviewHomeScreen() {
    val fakeTravels = listOf(
        Travel(
            id = 1,
            destination = "Paris",
            type = "Lazer",
            startDate = System.currentTimeMillis(),
            endDate = System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000,
            budget = 5000.0
        ),
        Travel(
            id = 2,
            destination = "São Paulo",
            type = "Negócio",
            startDate = System.currentTimeMillis() + 5 * 24 * 60 * 60 * 1000,
            endDate = System.currentTimeMillis() + 8 * 24 * 60 * 60 * 1000,
            budget = 2000.0
        )
    )

    /*val fakeViewModel = object : HomeViewModel(dao = FakeTravelDao()) {
        override val travels = MutableStateFlow(fakeTravels)
    }*/

    MaterialTheme {
        HomeScreen(
            onEditTravel = {},
            travelDao = TODO() // só pra satisfazer o parâmetro, não será usado
        )
    }
}
