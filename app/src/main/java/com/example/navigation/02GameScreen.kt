package com.example.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun GameScreen(navController: NavController, dificultadSeleccionada: String) {
    var estado by remember { mutableStateOf("Jugando") }
    var attempts by remember { mutableIntStateOf(6) }
    var intentosConsumidos by remember { mutableIntStateOf(0) }
    var secretWord by remember { mutableStateOf("patata".uppercase()) }
    var hiddenWord by remember { mutableStateOf("_".repeat(secretWord.length)) }
    var hangmanPicture by remember { mutableIntStateOf(R.drawable.hangman01)}
    var letrasAbecedario by remember {
        mutableStateOf(
            arrayOf(
                'A',
                'B',
                'C',
                'D',
                'E',
                'F',
                'G',
                'H',
                'I',
                'J',
                'K',
                'L',
                'M',
                'N',
                'Ñ',
                'O',
                'P',
                'Q',
                'R',
                'S',
                'T',
                'U',
                'V',
                'W',
                'X',
                'Y',
                'Z'
            )
        )
    }
    var hangmanPicturesArray = arrayOf(
        R.drawable.hangman01,
        R.drawable.hangman02,
        R.drawable.hangman03,
        R.drawable.hangman04,
        R.drawable.hangman05,
        R.drawable.hangman06
    )
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
                    painterResource(id = hangmanPicture),
                    contentScale = ContentScale.FillBounds
                )
        )
        repeat(5) { rowIndex ->
            Row(modifier = Modifier.padding(5.dp)) {
                repeat(6) { colIndex ->
                    val letraIndex = rowIndex * 6 + colIndex
                    if (letraIndex < letrasAbecedario.size) {
                        Box(modifier = Modifier
                            .background(Color.White)
                            .height(50.dp)
                            .width(50.dp)
                            .padding(5.dp)
                            .border(width = 5.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
                            .clickable {
                                intentosConsumidos++
                                attempts--
                                val newHiddenWord = hiddenWord.toCharArray()
                                if (letrasAbecedario[letraIndex] in secretWord) {
                                    for (letra in secretWord.indices) {
                                        if (letrasAbecedario[letraIndex] == secretWord[letra]) {
                                            newHiddenWord[letra] = letrasAbecedario[letraIndex]
                                        }
                                    }
                                } else {
                                    hangmanPicture = hangmanPicturesArray[intentosConsumidos-1]
                                }
                                hiddenWord = String(newHiddenWord)
                                if (hiddenWord == secretWord) {
                                    estado = "Victoria"
                                } else if (attempts == 0) {
                                    estado = "Derrota"
                                }
                                if (estado !="Jugando") {
                                    navController.navigate(
                                        Routes.ResultScreen.createRoute(
                                            dificultadSeleccionada, estado, intentosConsumidos
                                        )
                                    )
                                }
                            }) {
                            Text(
                                text = letrasAbecedario[letraIndex].toString(),
                                color = Color.Black,
                                modifier = Modifier.align(Alignment.Center)
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
                "INTENTOS $attempts",
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
            .clickable {
            }) {
            Text(
                text = "ESCRIBIR", modifier = Modifier.align(Alignment.Center), color = Color.White
            )
        }
    }
}