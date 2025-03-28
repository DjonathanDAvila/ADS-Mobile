package com.example.trabalho_final

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trabalho_final.screens.LoginScreen
import com.example.trabalho_final.screens.RegisterUserScreen
import com.example.trabalho_final.screens.Screens
import com.example.trabalho_final.ui.theme.TrabalhofinalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrabalhofinalTheme {
                Surface() {
                    MyApp()
                }
            }
        }
    }
}


@Composable
fun MyApp() {
    val navController = rememberNavController()
    Scaffold {
        Column(
            modifier = Modifier.padding(it)
        ) {
            NavHost(
                navController = navController,
                startDestination = "LoginScreen"
            ) {
                composable(route = Screens.Login.route) {
                    LoginScreen {
                        navController.navigate(it)
                    }
                }
                composable(route = Screens.RegisterUser.route) {
                    RegisterUserScreen(
                        onNavigateTo = { navController.navigate(it) },
                        onBack = { navController.popBackStack() })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TrabalhofinalTheme {
        MyApp()
    }
}