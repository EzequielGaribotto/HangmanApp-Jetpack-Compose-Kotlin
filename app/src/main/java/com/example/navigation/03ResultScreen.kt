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
import androidx.compose.foundation.shape.RoundedCornerShape
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
    secretWord: String
) {
    val titulo by remember {
        mutableStateOf(
            if (estado == "Victoria") "Felicidades"
            else "Oh no"
        )
    }
    val mensaje by remember {
        mutableStateOf(
            if (estado == "Victoria") "Has ganado después de $intentosConsumidos intentos"
            else "Has consumido todos los intentos sin éxito :(\n" + "La palabra secreta era $secretWord"
        )
    }
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
            if (titulo.isNotBlank()) {
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        titulo,
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Black,
                        fontSize = 43.sp
                    )
                }
            }
            if (mensaje.isNotBlank()) {
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        mensaje, modifier = Modifier.align(Alignment.Center), color = Color.Black
                    )
                }
            }
// BOTON "JUGAR DE NUEVO"

// BOTON "JUGAR DE NUEVO"

            Box(modifier = Modifier
                .background(Color.Red, shape = RoundedCornerShape(12.dp))
                .height(125.dp)
                .width(240.dp)
                .align(alignment = Alignment.CenterHorizontally)
                .clickable {
                    navController.navigate(Routes.GameScreen.createRoute(dificultadSeleccionada))
                },
                contentAlignment = Alignment.Center) {

                Text(
                    text = "Jugar de nuevo",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White,
                    fontSize = 40.sp
                )
            }

            // BOTÓN "VOLVER AL MENÚ"
            Spacer(modifier = Modifier.height(15.dp))
            Box(modifier = Modifier
                .background(Color.Red, shape = RoundedCornerShape(12.dp))
                .height(125.dp)
                .width(240.dp)
                .clickable {
                    navController.navigate(Routes.MenuScreen.route)
                }) {
                Text(
                    text = "Menu",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White,
                    fontSize = 40.sp
                )
            }
        }
    }
}
