package com.example.tip_calculator.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tip_calculator.ui.theme.TipcalculatorTheme

@Composable
fun TipCalculatorMainScreen() {
    val tipCalculatorViewModel: TipCalculatorViewModel = viewModel()
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TipCalculatorFields(tipCalculatorViewModel)
        }
    }
}

@Composable
fun TipCalculatorFields(tipCalculatorViewModel: TipCalculatorViewModel) {
    var tipCalculator = tipCalculatorViewModel.uiState.collectAsState()
    var sliderPosition by remember { mutableFloatStateOf(tipCalculator.value.custom) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(end = 8.dp),
            text = "Amount"
        )
        OutlinedTextField(
            value = "$${tipCalculator.value.amount}",
            onValueChange = { tipCalculatorViewModel.onAmountChange(it) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(end = 8.dp),
            text = "Custom %"
        )
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it }
        )
        Text(text = sliderPosition.toString())
    }

//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        horizontalArrangement = Arrangement.Absolute.SpaceAround,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(
//            modifier = Modifier.padding(end = 8.dp),
//            text = "${tipCalculator.value.default}%"
//        )
//
//        Text(
//            modifier = Modifier.padding(end = 8.dp),
//            text = "${tipCalculator.value.custom}%"
//        )
//    }

//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        horizontalArrangement = Arrangement.Absolute.SpaceAround,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(
//            modifier = Modifier.padding(end = 8.dp),
//            text = "Tip"
//        )
//
//        OutlinedTextField(
//            modifier = Modifier.width(),
//            value = "$${tipCalculator.value.tip}",
//            onValueChange = { tipCalculatorViewModel.onAmountChange(it) },
//        )
//
//        OutlinedTextField(
//            value = "$${tipCalculator.value.tipCustom}",
//            onValueChange = { tipCalculatorViewModel.onAmountChange(it) },
//        )
//    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "${tipCalculator.value.default}%")
            Text(text = "${tipCalculator.value.custom}%")
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Tip", modifier = Modifier.weight(1f))

                OutlinedTextField(
                    value = "$${tipCalculator.value.tip}",
                    onValueChange = {},
                    modifier = Modifier.weight(2f)
                )

                OutlinedTextField(
                    value = "$${tipCalculator.value.tipCustom}",
                    onValueChange = {},
                    modifier = Modifier.weight(2f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Total", modifier = Modifier.weight(1f))

                OutlinedTextField(
                    value = "$${tipCalculator.value.total}",
                    onValueChange = {},
                    modifier = Modifier.weight(2f)
                )

                OutlinedTextField(
                    value = "$${tipCalculator.value.totalCustom}",
                    onValueChange = {},
                    modifier = Modifier.weight(2f)
                )
            }
        }
    }
}


@Composable
@Preview(showSystemUi = true, showBackground = true, device = "id:Nexus 5")
fun TipCalculatorPreview() {
    TipcalculatorTheme {
        TipCalculatorMainScreen()
    }
}