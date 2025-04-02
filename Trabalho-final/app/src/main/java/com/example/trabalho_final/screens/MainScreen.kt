package com.example.trabalho_final.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocationAlt
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateTo: (String) -> Unit,
    onBack: () -> Unit
) {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Travel")
                }
            )
        },
        bottomBar = {
            BottomNavigation {
                val backStack = navController.currentBackStackEntryAsState()
                val currentDestination = backStack.value?.destination
                BottomNavigationItem(
                    selected =
                    currentDestination?.hierarchy?.any {
                        it.route == "MainScreen"
                    } == true,
                    onClick = { navController.navigate("HomeScreen") },
                    icon = {
                        Icon(imageVector = Icons.Default.Home, contentDescription = "Home Screen")
                    }
                )

                BottomNavigationItem(
                    selected =
                    currentDestination?.hierarchy?.any {
                        it.route == "MainScreen"
                    } == true,
                    onClick = { navController.navigate("TravelScreen") },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.AddLocationAlt,
                            contentDescription = "Travel Screen"
                        )
                    }
                )

                BottomNavigationItem(
                    selected =
                    currentDestination?.hierarchy?.any {
                        it.route == "MainScreen"
                    } == true,
                    onClick = { navController.navigate("AboutScreen") },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Assessment,
                            contentDescription = "About Screen"
                        )
                    }
                )
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            NavHost(navController = navController, startDestination = "HomeScreen") {
                composable(Screens.Home.route) {
                    HomeScreen()
                }
                composable(Screens.Travel.route) {
                    TravelScreen()
                }
                composable(Screens.About.route) {
                    AboutScreen()
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun PreviewMainScreen() {
    MaterialTheme {
        MainScreen(onNavigateTo = {}, onBack = {})
    }
}