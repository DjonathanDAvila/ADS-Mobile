package com.example.trabalho_final.screens

import android.widget.Toast
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trabalho_final.components.ErrorDialog
import com.example.trabalho_final.components.MyInputField
import com.example.trabalho_final.components.MyPasswordField
import com.example.trabalho_final.database.AppDataBase
import com.example.trabalho_final.viewmodel.RegisterUserViewModel
import com.example.trabalho_final.viewmodel.RegisterUserViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterUserScreen(
    onNavigateTo: (String) -> Unit,
    onBack: () -> Unit
) {
    val ctx = LocalContext.current
    val userDao = AppDataBase.getDatabase(ctx).userDao()
    val registerUserViewModel: RegisterUserViewModel =
        viewModel(
            factory =
            RegisterUserViewModelFactory(userDao)
        )
    var registerUser = registerUserViewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 46.dp, start = 18.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = { onBack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(text = "Create Account", fontWeight = FontWeight.Bold, fontSize = 32.sp)
            Spacer(modifier = Modifier.padding(12.dp))

            MyInputField(
                label = "Full Name",
                value = registerUser.value.fullName,
                onValueChange = {
                    registerUserViewModel.onUserChange(it)
                },
                icon = Icons.Default.Person,
                contentDescription = "Full Name Icon"
            )

            Spacer(modifier = Modifier.padding(8.dp))

            MyInputField(
                label = "Email",
                value = registerUser.value.email,
                onValueChange = {
                    registerUserViewModel.onEmailChange(it)
                },
                icon = Icons.Default.Email,
                contentDescription = "Email Icon"
            )

            Spacer(modifier = Modifier.padding(8.dp))

            MyPasswordField(
                label = "Password",
                value = registerUser.value.password,
                passwordConfirm = "",
                confirm = false,
                onValueChange = {
                    registerUserViewModel.onPasswordChange(it)
                }
            )
//            Spacer(modifier = Modifier.padding(10.dp))

            MyPasswordField(
                label = "Password confirm",
                value = registerUser.value.confirmPassword,
                passwordConfirm = registerUser.value.password,
                confirm = true,
                onValueChange = {
                    registerUserViewModel.onConfirmPassword(it)
                }
            )

            Spacer(modifier = Modifier.padding(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        registerUserViewModel.register()
                    }
                ) {
                    Text(text = "Sing Up")
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Arrow Icon"
                    )
                    if (registerUser.value.errorMessage.isNotBlank()) {
                        ErrorDialog(
                            error = registerUser.value.errorMessage,
                            onDismissRequest = {
                                registerUserViewModel.cleanDisplayValues()
                            },
                        )
                    }
                    LaunchedEffect(registerUser.value.isSaved) {
                        if (registerUser.value.isSaved) {
                            Toast.makeText(
                                ctx, "User registered",
                                Toast.LENGTH_SHORT
                            ).show()
                            registerUserViewModel.cleanDisplayValues()
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Already have a account? ")

            Text(
                text = "Sign in",
                color = Color.Blue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onNavigateTo(Screens.RegisterUser.route) } // Navegação ao clicar
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun PrevireRegisterUser() {
    MaterialTheme {
        RegisterUserScreen(onNavigateTo = {}, onBack = {})
    }
}