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
                        startDestination = Routes.SplashScreen.route
                    ) {
                        composable(Routes.SplashScreen.route) { SplashScreen(navigationController) }

                        composable(Routes.MenuScreen.route) { MenuScreen(navigationController) }
                        composable(
                            Routes.GameScreen.route,
                            arguments = listOf(navArgument("dificultadSeleccionada") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            GameScreen(
                                navigationController,
                                backStackEntry.arguments?.getString("dificultadSeleccionada")
                                    .orEmpty()
                            )
                        }
                        composable(
                            Routes.ResultScreen.route,
                            arguments = listOf(navArgument("dificultadSeleccionada") {
                                type = NavType.StringType
                            }, navArgument("estado") {
                                type = NavType.StringType
                            }, navArgument("intentosConsumidos") {
                                type = NavType.IntType
                            }, navArgument("secretWord") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            ResultScreen(
                                navigationController,
                                backStackEntry.arguments?.getString("dificultadSeleccionada").orEmpty(),
                                backStackEntry.arguments?.getString("estado").orEmpty(),
                                backStackEntry.arguments?.getInt("intentosConsumidos") ?: 0,
                                backStackEntry.arguments?.getString("secretWord").orEmpty()
                            )
                        }
                    }
                }
            }
        }
    }
}