package com.example.navigation2025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigation2025.screen.MainScreen
import com.example.navigation2025.screen.ScreenA
import com.example.navigation2025.screen.ScreenB
import com.example.navigation2025.screen.ScreenC
import com.example.navigation2025.ui.theme.Navigation2025Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Navigation2025Theme {
                Surface {
                    MyApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Navigation App")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            NavHost(
                navController = navController,
                startDestination = "MainScreen"
            ) {
                composable(route = "MainScreen") {
                    MainScreen(onNavigateTo = {
                        navController.navigate(it)
                    })
                }

                composable(route = "ScreenA") {
                    ScreenA(
                        onNavigateTo = { navController.navigate(it) },
                        onBack = { navController.popBackStack() })
                }

                composable(route = "ScreenB") {
                    ScreenB(
                        onNavigateTo = { navController.navigate(it) },
                        onBack = { navController.popBackStack() })
                }

                composable(route = "ScreenC") {
                    ScreenC(onNavigateTo = {
                        navController.navigate(it)
                    })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Navigation2025Theme {
        MyApp()
    }
}