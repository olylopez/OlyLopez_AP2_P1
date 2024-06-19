package edu.ucne.olylopez_ap2_p1.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    object ServicioList : Screen()

    @Serializable
    data class ServicioRegistro(val servicioId: Int) : Screen()

    @Serializable
    object ApiList : Screen()
}

