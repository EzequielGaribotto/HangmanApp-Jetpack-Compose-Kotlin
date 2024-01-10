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
    "calabaza", "arnaldo", "remolacha", "rabanito", "apio",
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

data class ParametrosJuego(
    val intentos: Int = 0,
    val longMaxPalabra: Int = 0,
    val longMinPalabra: Int = 0
)

data class EstadoJuego(
    var intentosConsumidos: Int = 0,
    var fallos:Int = 0,
    var indexImagen: Int,
    var ahorcado: Int = R.drawable.hangman00,
    var palabraEscondida: String,
    var letrasPresionadas: Array<Boolean>,
    var estadoUsuario: String
)

const val filas = 5
const val columnas = 6

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(navController: NavController, dificultadSeleccionada: String) {
    val params by remember { mutableStateOf(obtenerParametros(dificultadSeleccionada)) }
    val palabraSecreta by remember { mutableStateOf(encontrarPalabraValida(params, palabras)) }
    val state by remember {
        mutableStateOf(
            EstadoJuego(
                intentosConsumidos = 0,
                indexImagen = imagenesAhorcado.size - params.intentos,
                ahorcado = R.drawable.hangman00,
                palabraEscondida = "_".repeat(palabraSecreta.length),
                letrasPresionadas = Array(letrasAbecedario.size) { false },
                estadoUsuario = "Jugando"
            )
        )
    }
    var mostrarTextField by remember { mutableStateOf(false) }
    var palabraEscrita by remember { mutableStateOf("") }
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
        if (state.fallos < params.intentos) repeat(filas) { filaIndex ->
            Row(modifier = Modifier.padding(5.dp)) {
                repeat(columnas) { colIndex ->
                    val letraIndex = filaIndex * columnas + colIndex
                    if (letraIndex < letrasAbecedario.size) {
                        var colorDeFondoCaracter by remember { mutableStateOf(Color.White) }
                        Box(modifier = Modifier
                            .height(50.dp)
                            .width(50.dp)
                            .padding(5.dp)
                            .border(
                                width = 5.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .background(colorDeFondoCaracter, shape = RoundedCornerShape(8.dp))
                            .clickable {
                                if (!state.letrasPresionadas[letraIndex]) {
                                    colorDeFondoCaracter =
                                        actualizarCaracter(state, letraIndex, palabraSecreta)
                                    actualizarPalabraEscondida(
                                        state,
                                        palabraSecreta,
                                        letrasAbecedario,
                                        letraIndex
                                    )
                                    actualizarEstadoDelJuego(
                                        state,
                                        params,
                                        palabraSecreta,
                                        palabraEscrita
                                    )
                                    intentarNavegar(
                                        state,
                                        navController,
                                        dificultadSeleccionada,
                                        palabraSecreta
                                    )
                                }
                            }) {
                            Text(
                                text = letrasAbecedario[letraIndex].toString(),
                                color = Color.Black,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .background(colorDeFondoCaracter),
                            )
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                "INTENTOS: ${params.intentos-state.fallos}",
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
                        if (palabraEscrita != palabraSecreta) dibujarSiguienteImagen(state)
                        actualizarEstadoDelJuego(state, params, palabraSecreta, palabraEscrita)
                        intentarNavegar(
                            state,
                            navController,
                            dificultadSeleccionada,
                            palabraSecreta
                        )
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

fun obtenerParametros(dificultad: String): ParametrosJuego {
    val ParametrosJuego: ParametrosJuego
    ParametrosJuego = when (dificultad) {
        "Muy fácil" -> ParametrosJuego(6, 6, 0)
        "Fácil" -> ParametrosJuego(6, 8, 0)
        "Intermedia" -> ParametrosJuego(6, 10, 0)
        "Alta" -> ParametrosJuego(5, 12, 0)
        else -> ParametrosJuego(4, 20, 7)
    }
    return ParametrosJuego
}

fun encontrarPalabraValida(params: ParametrosJuego, palabras: Array<String>): String {
    var palabraValida = false
    var indiceRandom = (palabras.indices).random()

    while (!palabraValida) {
        if (palabras[indiceRandom].length in params.longMinPalabra until params.longMaxPalabra) {
            palabraValida = true
        } else {
            indiceRandom = (palabras.indices).random()
        }
    }
    val palabra = palabras[indiceRandom].uppercase()
    return palabra
}

fun actualizarCaracter(state: EstadoJuego, letraIndex: Int, palabraSecreta: String): Color {
    val colorDeFondoCaracter: Color
    state.letrasPresionadas[letraIndex] = true
    colorDeFondoCaracter = if (letrasAbecedario[letraIndex] in palabraSecreta.normalize()) {
        Color.Green
    } else {
        Color.Red
    }
    return colorDeFondoCaracter
}

fun actualizarPalabraEscondida(
    state: EstadoJuego,
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
        dibujarSiguienteImagen(state)
    }
    state.palabraEscondida = String(newHiddenWord)
}

fun dibujarSiguienteImagen(state: EstadoJuego) {
    state.fallos++
    state.ahorcado = imagenesAhorcado[state.indexImagen]
    state.indexImagen++
}

fun actualizarEstadoDelJuego(
    state: EstadoJuego,
    params: ParametrosJuego,
    palabraSecreta: String,
    palabraEscrita: String
) {
    state.intentosConsumidos++
    var estado = "Jugando"
    if (state.palabraEscondida == palabraSecreta || palabraEscrita == palabraSecreta.normalize()) {
        estado = "Victoria"
    } else if (params.intentos == state.fallos) {
        estado = "Derrota"
    }
    state.estadoUsuario = estado
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

fun intentarNavegar(
    state: EstadoJuego,
    navController: NavController,
    dificultadSeleccionada: String,
    palabraSecreta: String
) {
    if (state.estadoUsuario != "Jugando") {
        navController.navigate(
            Routes.ResultScreen.createRoute(
                dificultadSeleccionada,
                state.estadoUsuario,
                state.intentosConsumidos,
                palabraSecreta
            )
        )
    }
}