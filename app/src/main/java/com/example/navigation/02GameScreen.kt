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
    "calabaza", "calabacín", "remolacha", "rabanito", "apio",
    "económico", "público", "médico", "mágico", "cántaro",
    "cómico", "cívico", "lúdico", "pálido", "círculo",
    "hélice", "pócima", "lúgubre", "cámara", "póster",
    "místico", "móvil", "césped", "módulo", "cántico",
    "gárgola", "cántico", "plástico", "gótico", "cítrico",
    "público", "rúbrica", "médula", "bóveda", "púlpito",
    "títere", "médico", "óvalo", "fútil", "cóctel",
    "lógico", "déspota", "cúspide", "síntoma", "límite",
    "tópico", "fútil", "rúbrica", "póliza", "cólera",
    "árbol", "éxito", "índice", "órgano", "último",
    "único", "época", "ángel", "ánfora", "ánimo",
    "área", "fácil", "héroe", "dólar", "técnico",
    "rápido", "música", "órbita", "órden", "óxido",
    "sólido", "sátiro", "rábano", "título", "tóxico",
    "célula", "bólido", "álbum", "débilidad", "mágico",
    "récord", "tótemico", "átomo", "céfiro", "ámbar",
    "nórdico", "cúbico", "gélido", "tórax", "cálido",
    "fórceps", "móvil", "órdenes", "céntimo", "déficit",
    "límite", "lógico"

)

val imagenesAhorcado = arrayOf(
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

data class GameParameters(
    var intentos: Int = 0,
    val maxWordLength: Int = 0,
    val minWordLength: Int = 0
)

data class GameState(
    var intentosConsumidos: Int = 0,
    var indexImagen: Int,
    var ahorcado: Int = R.drawable.hangman00,
    var palabraEscondida: String,
    var letrasPresionadas: Array<Boolean>,
    var userState: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(navController: NavController, dificultadSeleccionada: String) {
    val params by remember { mutableStateOf(getGameParameters(dificultadSeleccionada)) }
    val palabraSecreta by remember { mutableStateOf(encontrarPalabraValida(params, palabras)) }
    val state by remember {
        mutableStateOf(
            GameState(
                intentosConsumidos = 0,
                indexImagen = imagenesAhorcado.size - params.intentos,
                ahorcado = R.drawable.hangman00,
                palabraEscondida = "_".repeat(palabraSecreta.length),
                letrasPresionadas = Array(letrasAbecedario.size) { false },
                userState = "Jugando"
            )
        )
    }
    val filas by remember { mutableIntStateOf(5) }
    val columnas by remember { mutableIntStateOf(6) }
    var mostrarTextField by remember { mutableStateOf(false) }
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
                    state.palabraEscondida,
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Black,
                    fontSize = 43.sp,
                    letterSpacing = 5.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .size(150.dp)
                .paint(
                    painterResource(id = state.ahorcado),
                    contentScale = ContentScale.FillBounds
                )
        )
        if (params.intentos > 0) repeat(filas) { filaIndex ->
            Row(modifier = Modifier.padding(5.dp)) {
                repeat(columnas) { colIndex ->
                    val letraIndex = filaIndex * columnas + colIndex
                    if (letraIndex < letrasAbecedario.size) {
                        var backgroundCharColor by remember { mutableStateOf(Color.White) }
                        Box(modifier = Modifier
                            .height(50.dp)
                            .width(50.dp)
                            .padding(5.dp)
                            .border(
                                width = 5.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .background(backgroundCharColor, shape = RoundedCornerShape(8.dp))
                            .clickable {
                                if (!state.letrasPresionadas[letraIndex]) {
                                    backgroundCharColor = actualizarCaracter(state, letraIndex, palabraSecreta)
                                    actualizarPalabraEscondida(state, params, palabraSecreta, letrasAbecedario, letraIndex)
                                    actualizarEstadoDelJuego(state, params, palabraSecreta, palabraEscrita)
                                    tryNavigate(state, navController, dificultadSeleccionada, palabraSecreta)
                                }
                            }) {
                            Text(
                                text = letrasAbecedario[letraIndex].toString(),
                                color = Color.Black,
                                modifier = Modifier
                                    .align(Alignment.Center)
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
                "INTENTOS: ${params.intentos}",
                modifier = Modifier.align(Alignment.Center),
                color = Color.Black,
                fontSize = 43.sp
            )
        }
    }
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(Color.Red)
            .height(50.dp)
            .clickable { mostrarTextField = !mostrarTextField }) {
            Text(
                text = "ESCRIBIR",
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        }
        if (mostrarTextField) {
            TextField(
                value = palabraEscrita.uppercase(),
                onValueChange = { palabraEscrita = it.uppercase() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Box(modifier = Modifier
                .background(Color.Red, shape = RoundedCornerShape(12.dp))
                .height(50.dp)
                .width(120.dp)
                .clickable {
                    if (palabraEscrita != "") {
                        if (palabraEscrita != palabraSecreta) drawNextImage(state,params)
                        actualizarEstadoDelJuego(state, params, palabraSecreta, palabraEscrita)
                        tryNavigate(state, navController, dificultadSeleccionada, palabraSecreta)
                    }
                    mostrarTextField = false
                }) {
                Text(
                    text = "Probar",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White,
                )
            }
        }
    }
}

fun getGameParameters(dificultad: String): GameParameters {
    val gameParameters: GameParameters
    gameParameters = when (dificultad) {
        "Muy fácil" -> GameParameters(6, 6, 0)
        "Fácil" -> GameParameters(6, 8, 0)
        "Intermedia" -> GameParameters(6, 10, 0)
        "Alta" -> GameParameters(5, 12, 0)
        else -> GameParameters(4, 20, 7)
    }
    return gameParameters
}

fun encontrarPalabraValida(params: GameParameters, palabras: Array<String>): String {
    var validWord = false
    var randomIndex = (palabras.indices).random()

    while (!validWord) {
        if (palabras[randomIndex].length in params.minWordLength until params.maxWordLength) {
            validWord = true
        } else {
            randomIndex = (palabras.indices).random()
        }
    }
    val palabraValida = palabras[randomIndex].uppercase()
    return palabraValida
}

fun actualizarCaracter(state: GameState, letraIndex: Int, palabraSecreta: String): Color {
    val backgroundCharColor:Color
    state.letrasPresionadas[letraIndex] = true
    backgroundCharColor = if (letrasAbecedario[letraIndex] in palabraSecreta.normalize()) {
        Color.Green
    } else {
        Color.Red
    }
    return backgroundCharColor
}

fun actualizarPalabraEscondida(
    state: GameState,
    params: GameParameters,
    palabraSecreta: String,
    letrasAbecedario: Array<Char>,
    letraIndex: Int
) {
    val newHiddenWord = state.palabraEscondida.toCharArray()
    for (letra in palabraSecreta.indices) {
        if (letrasAbecedario[letraIndex] == palabraSecreta[letra].toString().normalize().single()) {
            newHiddenWord[letra] = palabraSecreta[letra]
        }
    }
    if (String(newHiddenWord) == state.palabraEscondida) {
        drawNextImage(state,params)
    }
    state.palabraEscondida = String(newHiddenWord)
}

fun drawNextImage(state: GameState, params: GameParameters) {
    params.intentos--
    state.ahorcado = imagenesAhorcado[state.indexImagen]
    state.indexImagen++
}

fun actualizarEstadoDelJuego(
    state: GameState,
    params: GameParameters,
    palabraSecreta: String,
    palabraEscrita:String
) {
    state.intentosConsumidos++
    var estado = "Jugando"
    if (state.palabraEscondida == palabraSecreta || palabraEscrita == palabraSecreta.normalize()) {
        estado = "Victoria"
    } else if (params.intentos == 0) {
        estado = "Derrota"
    }
    state.userState = estado
}

fun String.normalize(): String {
    val stringNormalitzat: String
    stringNormalitzat = this.replace("Á", "A")
        .replace("É", "E")
        .replace("Í", "I")
        .replace("Ó", "O")
        .replace("Ú", "U")
    return stringNormalitzat
}

fun tryNavigate(state: GameState, navController: NavController, dificultadSeleccionada: String, palabraSecreta: String) {
    if (state.userState != "Jugando") {
        navController.navigate(
            Routes.ResultScreen.createRoute(
                dificultadSeleccionada,
                state.userState,
                state.intentosConsumidos,
                palabraSecreta
            )
        )
    }
}