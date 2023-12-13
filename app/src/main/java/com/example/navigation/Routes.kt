package com.example.navigation

sealed class Routes(val route: String) {
    object SplashScreen : Routes("splash_screen")
    object MenuScreen : Routes("menu_screen")
    object GameScreen : Routes("game_screen/{dificultadSeleccionada}") {
        fun createRoute(dificultadSeleccionada: String) = "game_screen/$dificultadSeleccionada"
    }

    object ResultScreen : Routes("result_screen/{dificultadSeleccionada}/{estado}/{intentosConsumidos}") {
        fun createRoute(dificultadSeleccionada: String, estado:String, intentosConsumidos:Int) =
            "result_screen/$dificultadSeleccionada/$estado/$intentosConsumidos"
    }
}