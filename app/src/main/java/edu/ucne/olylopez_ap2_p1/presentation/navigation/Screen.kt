package edu.ucne.olylopez_ap2_p1.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    object List : Screen()

    @Serializable
    data class Registro(val tecnicoId: Int) : Screen()


}

