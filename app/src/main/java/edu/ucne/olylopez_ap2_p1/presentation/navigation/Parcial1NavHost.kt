package edu.ucne.olylopez_ap2_p1.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.olylopez_ap2_p1.presentation.Servicio.ServicioListScreen
import edu.ucne.olylopez_ap2_p1.presentation.Servicio.ServicioViewModel
import edu.ucne.olylopez_ap2_p1.repository.ServicioRepository
import edu.ucne.olylopez_ap2_p1.data.local.database.ServicioDb
import edu.ucne.olylopez_ap2_p1.presentation.Servicio.ServicioScreen

@Composable
fun Parcial1NavHost(navHostController: NavHostController) {
   /* NavHost(
        navController = navHostController,
        startDestination = Screen.ServicioList

    ) {
        composable<Screen.ServicioList>{
            ServicioListScreen(
                viewModel = viewModel { ServicioViewModel(repository, 0) },
                onDeleteServicio = { },
                onVerServicio =  {
                    navHostController.navigate(Screen.ServicioRegistro(it.servicioId ?:0))
                },
                onAddServicio = {navHostController.navigate(Screen.ServicioRegistro(0))
                },
                navController =navHostController
            )
        }
        composable<Screen.ServicioRegistro>{
            val args = it.toRoute<Screen.ServicioRegistro>()
            ServicioScreen(viewModel = viewModel { ServicioViewModel(repository, args.servicioId) },
                navController = navHostController)
        }
    }*/

}





