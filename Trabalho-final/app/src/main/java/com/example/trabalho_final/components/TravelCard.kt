package com.example.trabalho_final.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BeachAccess
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trabalho_final.entity.Travel
import com.example.trabalho_final.entity.enums.TravelType
import com.example.trabalho_final.entity.enums.toDisplayName
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TravelCard(
    travel: Travel,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onSuggestionClick: () -> Unit
) {
    val formatter = remember { DateTimeFormatter.ofPattern("dd/MM/yyyy") }

    val icon = when (travel.type) {
        TravelType.NEGOCIO -> Icons.Default.Work
        TravelType.LAZER -> Icons.Default.BeachAccess
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = travel.type.toDisplayName(), modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = travel.destination, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "De ${travel.startDate.format(formatter)} até ${travel.endDate.format(formatter)}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "R$ ${travel.budget}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = travel.type.toDisplayName(),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            IconButton(onClick = onSuggestionClick) {
                Icon(Icons.Default.Map, contentDescription = "Ver roteiro")
            }
        }
    }
}

