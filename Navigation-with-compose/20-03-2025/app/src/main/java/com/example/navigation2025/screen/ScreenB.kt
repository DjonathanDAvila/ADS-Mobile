package com.example.navigation2025.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ScreenB(
    onNavigateTo: (String) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Screen B",
            style = MaterialTheme.typography.titleLarge
        )

        Button(onClick = { onBack() }) {
            Text(
                text = "Back"
            )
        }

        Button(onClick = { onNavigateTo("ScreenC") }) {
            Text(
                text = "Go to screen C"
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun PreviewScreenB() {
    MaterialTheme {
        ScreenB(onNavigateTo = {}, onBack = {})
    }
}