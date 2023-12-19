package com.example.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

val palabras = arrayOf(
    "manzana", "pera", "platano", "uva", "sandia",
    "kiwi", "fresa", "mango", "melocoton", "ciruela",
    "naranja", "limon", "pomelo", "mandarina", "cereza",
    "frambuesa", "arandano", "granada", "higo", "papaya",
    "piña", "coco", "avellana", "almendra", "cacahuete",
    "nuez", "castaña", "cereza", "ciruela", "uva",
    "pimiento", "tomate", "berenjena", "calabacin", "pepino",
    "zanahoria", "patata", "cebolla", "ajo", "lechuga",
    "espinaca", "coliflor", "brocoli", "repollo", "alcachofa",
    "calabaza", "calabacín", "remolacha", "rabanito", "apio"
)

val hangmanPictures = arrayOf(
    R.drawable.hangman01,
    R.drawable.hangman02,
    R.drawable.hangman03,
    R.drawable.hangman04,
    R.drawable.hangman05,
    R.drawable.hangman06
)

val letrasAbecedario =
        arrayOf(
            'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'Ñ', 'O', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z'
        )

data class GameParameters(var attempts: Int = 0, val maxWordLength: Int = 0, val minWordLength: Int = 0)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(navController: NavController, dificultadSeleccionada: String) {
    var estado by remember { mutableStateOf("Jugando") }
    val params by remember { mutableStateOf( getGameParameters(dificultadSeleccionada) ) }
    var intentosConsumidos by remember { mutableIntStateOf(0) }
    val secretWord by remember { mutableStateOf( findValidWord(params,palabras) ) }
    var hiddenWord by remember { mutableStateOf( "_".repeat(secretWord.length) ) }
    var hangmanState by remember { mutableIntStateOf( R.drawable.hangman00 )}
    var pictureIndex by remember { mutableIntStateOf(hangmanPictures.size - params.attempts)}
    val filas by remember { mutableIntStateOf(5) }
    val columnas by remember { mutableIntStateOf(6) }
    val pressedKeys by remember {
        mutableStateOf(
            Array(letrasAbecedario.size) { false }
        )
    }
    var showTextField by remember { mutableStateOf(false) }
    var palabraEscrita by remember { mutableStateOf("")}
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    hiddenWord,
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Black,
                    fontSize = 43.sp,
                    letterSpacing = 5.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .size(150.dp)
                .paint(
                    painterResource(id = hangmanState),
                    contentScale = ContentScale.FillBounds
                )
        )
        if (params.attempts > 0) repeat(filas) { rowIndex ->
            Row(modifier = Modifier.padding(5.dp)) {
                repeat(columnas) { colIndex ->
                    val letraIndex = rowIndex * columnas + colIndex
                    if (letraIndex < letrasAbecedario.size) {
                        var backgroundCharColor by remember { mutableStateOf(Color.White)}
                        Box(modifier = Modifier
                            .height(50.dp)
                            .width(50.dp)
                            .padding(5.dp)
                            .border(width = 5.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
                            .background(backgroundCharColor, shape = RoundedCornerShape(8.dp))
                            .clickable {
                                if (!pressedKeys[letraIndex]) {
                                    pressedKeys[letraIndex] = true
                                    intentosConsumidos++
                                    if (letrasAbecedario[letraIndex] in secretWord) {
                                        hiddenWord = updateHiddenWord(hiddenWord,secretWord,letrasAbecedario,letraIndex)
                                        backgroundCharColor = Color.Green
                                    } else {
                                        hangmanState = hangmanPictures[pictureIndex]
                                        backgroundCharColor = Color.Red
                                        pictureIndex++
                                        params.attempts--
                                    }
                                    estado = updateGameState(hiddenWord,secretWord,params,palabraEscrita)
                                    if (estado != "Jugando") {
                                        navController.navigate(Routes.ResultScreen.createRoute(dificultadSeleccionada, estado, intentosConsumidos, secretWord))
                                    }
                                }
                            }) {
                            Text(
                                text = letrasAbecedario[letraIndex].toString(),
                                color = Color.Black,
                                modifier = Modifier.align(Alignment.Center)
                                    .background(backgroundCharColor),
                            )
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                "INTENTOS: ${params.attempts}",
                modifier = Modifier.align(Alignment.Center),
                color = Color.Black,
                fontSize = 43.sp
            )
        }
    }
    Column(verticalArrangement = Arrangement.Bottom) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(Color.Red)
            .height(50.dp)
            .clickable { showTextField = true }) {
            Text(
                text = "ESCRIBIR", modifier = Modifier.align(Alignment.Center), color = Color.White
            )
        }
        if (showTextField) {
            TextField(
                value = palabraEscrita.uppercase(),
                onValueChange = { palabraEscrita = it.uppercase() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Button(onClick = {
                intentosConsumidos++
                showTextField = false
                if (palabraEscrita != secretWord) {
                    params.attempts--
                    hangmanState = hangmanPictures[pictureIndex]
                    pictureIndex++
                }
                estado = updateGameState(hiddenWord, secretWord, params, palabraEscrita)
                if (estado != "Jugando") {
                    navController.navigate(Routes.ResultScreen.createRoute(dificultadSeleccionada, estado, intentosConsumidos, secretWord))
                }
            }) {
                Text("Aceptar")
            }
        }
    }
}

fun getGameParameters(dificultad: String): GameParameters {
    return when (dificultad) {
        "Muy fácil" -> GameParameters(6, 6, 0)
        "Fácil" -> GameParameters(6, 8, 0)
        "Intermedia" -> GameParameters(6, 10, 0)
        "Alta" -> GameParameters(5, 12, 0)
        else -> GameParameters(4, 50, 7)
    }
}

fun findValidWord(params: GameParameters, palabras: Array<String>): String {
    var validWord = false
    var randomIndex = (palabras.indices).random()

    while (!validWord) {
        if (palabras[randomIndex].length in params.minWordLength..params.maxWordLength) {
            validWord = true
        } else {
            randomIndex = (palabras.indices).random()
        }
    }

    return palabras[randomIndex].uppercase()
}

fun updateHiddenWord(hiddenWord:String,secretWord:String,letrasAbecedario:Array<Char>,letraIndex:Int):String{
    val newHiddenWord = hiddenWord.toCharArray()
    for (letra in secretWord.indices) {
        if (letrasAbecedario[letraIndex] == secretWord[letra]) {
            newHiddenWord[letra] = letrasAbecedario[letraIndex]
        }
    }
    return String(newHiddenWord)
}

fun updateGameState(hiddenWord:String,secretWord:String, params:GameParameters, palabraEscrita:String):String {
    var estado = "Jugando"
    if (hiddenWord == secretWord || palabraEscrita == secretWord) {
        estado = "Victoria"
    } else if (params.attempts == 0) {
        estado = "Derrota"
    }
    return estado
}