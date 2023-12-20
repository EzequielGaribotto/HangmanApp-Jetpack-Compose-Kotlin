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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavController) {
    var dificultadSeleccionada by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val dificultades = listOf("Muy fácil", "Fácil", "Intermedia", "Alta", "Muy alta")

    // Fondo de pantalla
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Componentes
        Column(
            Modifier.padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Logo
            Box(
                modifier = Modifier
                    .paint(
                        painterResource(id = R.drawable.amongus),
                        contentScale = ContentScale.FillBounds
                    )
                    .size(120.dp),
            )

            // Placeholder

            OutlinedTextField(value = dificultadSeleccionada,
                onValueChange = { dificultadSeleccionada = it },
                enabled = false,
                readOnly = true,
                label = { Text("Dificultad") },
                modifier = Modifier
                    .clickable { expanded = true }
                    .fillMaxWidth())

            // Selección dificultad
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                dificultades.forEach { dificultad ->
                    DropdownMenuItem(text = { Text(text = dificultad) }, onClick = {
                        expanded = false
                        dificultadSeleccionada = dificultad
                    })
                }
            }
        }
        // BOTÓN "JUGAR"
        Box(modifier = Modifier
            .background(Color.Red)
            .height(50.dp)
            .width(120.dp)
            .clickable {
                if (dificultadSeleccionada != "") navController.navigate(
                    Routes.GameScreen.createRoute(
                        dificultadSeleccionada
                    )
                )
            }) {
            Text(
                text = "Play", modifier = Modifier.align(Alignment.Center), color = Color.White
            )
        }

        // BOTÓN "AYUDA"
        Spacer(modifier = Modifier.height(65.dp))
        var show by remember { mutableStateOf(false) }
        Box(modifier = Modifier
            .background(Color.Red)
            .height(50.dp)
            .width(120.dp)
            .clickable { show = true }) {
            Text(text = "Ayuda", modifier = Modifier.align(Alignment.Center), color = Color.White)
            MyDialog(show) { show = false }
        }
    }
}
@Composable
fun MyDialog(show: Boolean, onDismiss: () -> Unit) {
    if(show){
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                Modifier.background(Color.White)
                    .padding(24.dp).fillMaxWidth()) {
                Text(text =
                "El juego del ahorcado consiste en adivinar una palabra oculta aleatoria " +
                        "Para adivinar la palabra se tienen que escoger letras para revelar de la palabra " +
                        "que aparecerá en la parte superior de la pantalla, si la letra escogida está en la palabra " +
                        "oculta, se revelarán todas las letras coincidentes, en caso contrario, no.\n" +
                        "\n" +
                        "===DIFICULTADES===\n" +
                        "Muy fácil: 6 intentos, longitud de palabra inferior a 6 \n" +
                        "Fácil: 6 intentos, longitud de palabra inferior a 8 \n" +
                        "Intermedia: 6 intentos, longitud de palabra inferior a 10\n" +
                        "Alta: 5 intentos, longitud de palabra inferior a 12\n" +
                        "Muy alta: 4 intentos, longitud de palabra entre 7 y 20\n")
            }
        }
    }
}
