@file:Suppress("UNREACHABLE_CODE")

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.trabalho_final.database.AppDataBase
import com.example.trabalho_final.screens.Suggestion.SuggestionScreen
import com.example.trabalho_final.screens.home.HomeScreen
import com.example.trabalho_final.screens.travel.NewTravelScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateTo: (String) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val db = AppDataBase.getDatabase(context)
    val travelDao = db.travelDao()
    val suggestionDao = db.suggestionDao()
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
                    onClick = { navController.navigate(Screens.NewTravel.route) },
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
                    HomeScreen(
                        onEditTravel = { id ->
                            navController.navigate("${Screens.NewTravel.route}?id=$id")
                        },
                        travelDao = travelDao,
                        onViewSuggestion = { travelId, destination ->
                            navController.navigate("suggestion/$travelId/$destination")
                        }
                    )
                }
                composable(
                    route = "${Screens.NewTravel.route}?id={id}",
                    arguments = listOf(
                        navArgument("id") {
                            type = NavType.IntType
                            defaultValue = -1
                        }
                    )
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getInt("id") ?: -1
                    val travelId = if (id != -1) id else null

                    NewTravelScreen(
                        travelId = travelId,
                        onNavigateTo = {},
                        onBack = {}
                    )
                }

                composable(Screens.About.route) {
                    AboutScreen()
                }

                composable("suggestion/{travelId}/{destination}") { backStackEntry ->
                    val travelId = backStackEntry.arguments?.getString("travelId")?.toIntOrNull() ?: return@composable
                    val destination = backStackEntry.arguments?.getString("destination") ?: return@composable

                    SuggestionScreen(
                        travelId = travelId,
                        travelDao = travelDao,
                        suggestionDao = suggestionDao,
                        onBack = { navController.popBackStack() }
                    )
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