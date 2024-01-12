package com.example.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.navigation.ui.theme.NavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val navigationController = rememberNavController()
                    NavHost(
                        navController = navigationController,
                        startDestination = Routes.MenuScreen.route
                    ) {
                        composable(Routes.MenuScreen.route) { MenuScreen(navigationController) }
                        composable(
                            Routes.GameScreen.route,
                            arguments = listOf(navArgument("dificultad") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            GameScreen(
                                navigationController,
                                backStackEntry.arguments?.getString("dificultad")
                                    .orEmpty()
                            )
                        }
                        composable(
                            Routes.ResultScreen.route,
                            arguments = listOf(navArgument("dificultad") {
                                type = NavType.StringType
                            }, navArgument("estado") {
                                type = NavType.StringType
                            }, navArgument("intentos") {
                                type = NavType.IntType
                            }, navArgument("palabraSecreta") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            ResultScreen(
                                navigationController,
                                backStackEntry.arguments?.getString("dificultad").orEmpty(),
                                backStackEntry.arguments?.getString("estado").orEmpty(),
                                backStackEntry.arguments?.getInt("intentos") ?: 0,
                                backStackEntry.arguments?.getString("palabraSecreta").orEmpty()
                            )
                        }
                    }
                }
            }
        }
    }
}