package com.example.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ResultScreen(
    navController: NavController,
    dificultadSeleccionada: String,
    estado: String,
    intentosConsumidos: Int,
    secretWord:String
) {
    val missatge by remember { mutableStateOf(if (estado == "Victoria") "Has ganado después de $intentosConsumidos intentos"
                                              else "Has consumido todos los intentos sin éxito :(\n" +
                                                   "La palabra secreta era $secretWord") }
    val titol by remember { mutableStateOf( if (estado == "Victoria") "Felicidades"
                                            else "Oh no")}
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (titol.isNotBlank()) {
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        titol, modifier = Modifier.align(Alignment.Center), color = Color.Black, fontSize = 43.sp
                    )
                }
            }
            if (missatge.isNotBlank()) {
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        missatge, modifier = Modifier.align(Alignment.Center), color = Color.Black
                    )
                }
            }
            // BOTÓN "JUGAR DE NUEVO"
            Box(modifier = Modifier
                .background(Color.Red)
                .height(50.dp)
                .width(120.dp)
                .clickable {
                    navController.navigate(Routes.GameScreen.createRoute(dificultadSeleccionada))
                }) {
                Text(
                    text = "Jugar de nuevo",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }

            // BOTÓN "VOLVER AL MENÚ"
            Spacer(modifier = Modifier.height(65.dp))
            Box(modifier = Modifier
                .background(Color.Red)
                .height(50.dp)
                .width(120.dp)
                .clickable {
                    navController.navigate(Routes.MenuScreen.route)
                }) {
                Text(
                    text = "Menu", modifier = Modifier.align(Alignment.Center), color = Color.White
                )
            }
        }
    }
}
