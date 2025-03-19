package com.example.tip_calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.tip_calculator.screens.TipCalculatorMainScreen
import com.example.tip_calculator.ui.theme.TipcalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipcalculatorTheme {
                TipCalculatorMainScreen()
            }
        }
    }
}
