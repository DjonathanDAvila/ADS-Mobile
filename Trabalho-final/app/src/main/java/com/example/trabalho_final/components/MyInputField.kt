package com.example.trabalho_final.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun MyInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector? = null,
    contentDescription: String = ""
) {
//    var text by remember { mutableStateOf("") }
//    var isFocused by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        label = {
            Text(text = label)
        },
        onValueChange = { onValueChange(it) },
        leadingIcon = {
            if (icon != null) Icon(
                imageVector = icon,
                contentDescription = contentDescription
            )
        },
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
//            .onFocusChanged { isFocused = it.isFocused }
    )

}