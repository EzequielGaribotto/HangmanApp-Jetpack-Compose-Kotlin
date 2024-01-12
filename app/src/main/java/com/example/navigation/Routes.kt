package com.example.navigation

sealed class Routes(val route: String) {
    object MenuScreen : Routes("MenuScreen")
    object GameScreen : Routes("GameScreen/{dificultad}") {
        fun createRoute(dificultad: String) = "GameScreen/$dificultad"
    }

    object ResultScreen : Routes("ResultScreen/{dificultad}/{estado}/{intentos}/{palabraSecreta}") {
        fun createRoute(dificultad: String, estado:String, intentos:Int, palabraSecreta:String) =
            "ResultScreen/$dificultad/$estado/$intentos/$palabraSecreta"
    }
}