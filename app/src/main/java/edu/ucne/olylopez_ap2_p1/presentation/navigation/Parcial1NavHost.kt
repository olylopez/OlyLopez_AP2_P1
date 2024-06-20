package edu.ucne.olylopez_ap2_p1.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.olylopez_ap2_p1.data.remote.dto.TareasDto
import edu.ucne.olylopez_ap2_p1.presentation.Api.ApiListScreen
import edu.ucne.olylopez_ap2_p1.presentation.Api.TareaApiViewModel
import edu.ucne.olylopez_ap2_p1.presentation.Servicio.ServicioListScreen
import edu.ucne.olylopez_ap2_p1.presentation.Servicio.ServicioScreen
import edu.ucne.olylopez_ap2_p1.presentation.Servicio.ServicioViewModel
import edu.ucne.olylopez_ap2_p1.repository.ServicioRepository
import edu.ucne.olylopez_ap2_p1.repository.TareaRepository

@Composable
fun Parcial1NavHost(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.ServicioList
    ) {
        composable<Screen.ServicioList> {
            ServicioListScreen(
                onVerServicio = {
                    navHostController.navigate(Screen.ServicioRegistro(it.servicioId ?: 0))
                },
                onAddServicio = {
                    navHostController.navigate(Screen.ServicioRegistro(0))
                },
                navController = navHostController
            )
        }
        composable<Screen.ServicioRegistro> {
            ServicioScreen(
                navController = navHostController
            )
        }
        composable<Screen.ApiList> {
            ApiListScreen(
                navController = navHostController)
        }

    }
}








