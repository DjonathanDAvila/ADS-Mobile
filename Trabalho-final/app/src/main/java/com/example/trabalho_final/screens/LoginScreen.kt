package com.example.trabalho_final.screens

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trabalho_final.components.MyInputField
import com.example.trabalho_final.components.MyPasswordField

@Composable
fun LoginScreen(
    onNavigateTo: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(text = "Login", fontWeight = FontWeight.Bold, fontSize = 32.sp)
            Spacer(modifier = Modifier.padding(6.dp))
            Text(
                text = "Please sing in to continue",
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.padding(8.dp))


            MyInputField(
                label = "Email",
                value = "",
                onValueChange = {},
                icon = Icons.Default.Email,
                contentDescription = "Email Icon"
            )

            MyPasswordField(
                label = "Password",
                value = "",
                passwordConfirm = "",
                confirm = false,
                onValueChange = {}
            )

            Spacer(modifier = Modifier.padding(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { onNavigateTo(Screens.Main.route) }
                ) {
                    Text(text = "Login")
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Arrow Icon"
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Don't have an account? ")

            Text(
                text = "Sign up",
                color = Color.Blue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onNavigateTo(Screens.RegisterUser.route) } // Navegação ao clicar
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun PreviewLogin() {
    MaterialTheme {
        LoginScreen(onNavigateTo = {})
    }
}