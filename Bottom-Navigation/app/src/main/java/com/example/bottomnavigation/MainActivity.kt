package com.example.bottomnavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import com.example.bottomnavigation.Screens.MainScreen
import com.example.bottomnavigation.Screens.ProfileScreen
import com.example.bottomnavigation.ui.theme.BottomNavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BottomNavigationTheme {
                MyApp()
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
                    Text(text = "My App")
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
                    onClick = { navController.navigate("MainScreen") },
                    icon = {
                        Icon(imageVector = Icons.Default.Home, contentDescription = "Main Screen")
                    }
                )

                BottomNavigationItem(
                    selected =
                    currentDestination?.hierarchy?.any {
                        it.route == "MainScreen"
                    } == true,
                    onClick = { navController.navigate("ProfileScreen") },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile Screen"
                        )
                    }
                )
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            NavHost(navController = navController, startDestination = "MainScreen") {
                composable("MainScreen") {
                    MainScreen()
                }

                composable("ProfileScreen") {
                    ProfileScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BottomNavigationTheme {
        MyApp()
    }
}