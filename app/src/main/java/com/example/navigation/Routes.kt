package com.example.navigation

sealed class Routes(val route: String) {
    object SplashScreen : Routes("SplashScreen")
    object MenuScreen : Routes("MenuScreen")
    object GameScreen : Routes("GameScreen/{dificultadSeleccionada}") {
        fun createRoute(dificultadSeleccionada: String) = "GameScreen/$dificultadSeleccionada"
    }

    object ResultScreen : Routes("ResultScreen/{dificultadSeleccionada}/{estado}/{intentosConsumidos}/{secretWord}") {
        fun createRoute(dificultadSeleccionada: String, estado:String, intentosConsumidos:Int, secretWord:String) =
            "ResultScreen/$dificultadSeleccionada/$estado/$intentosConsumidos/$secretWord"
    }
}