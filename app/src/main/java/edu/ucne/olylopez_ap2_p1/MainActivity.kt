package edu.ucne.olylopez_ap2_p1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.room.Room
import edu.ucne.olylopez_ap2_p1.data.local.database.ServicioDb
import edu.ucne.olylopez_ap2_p1.data.local.entities.ServicioEntity
import edu.ucne.olylopez_ap2_p1.presentation.Servicio.ServicioListScreen
import edu.ucne.olylopez_ap2_p1.presentation.Servicio.ServicioScreen
import edu.ucne.olylopez_ap2_p1.presentation.Servicio.ServicioViewModel
import edu.ucne.olylopez_ap2_p1.presentation.navigation.Parcial1NavHost
import edu.ucne.olylopez_ap2_p1.presentation.navigation.Screen
import edu.ucne.olylopez_ap2_p1.repository.ServicioRepository
import edu.ucne.olylopez_ap2_p1.ui.theme.OlyLopez_AP2_P1Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var servicioDb: ServicioDb
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        servicioDb = Room.databaseBuilder(
            this,
            ServicioDb::class.java,
            "Servicio.db"
        )
            .fallbackToDestructiveMigration()
            .build()

        val repository = ServicioRepository(servicioDb.servicioDao())
        enableEdgeToEdge()
        setContent {
            OlyLopez_AP2_P1Theme {
                val navHostController = rememberNavController()
                Parcial1NavHost(
                    navHostController = navHostController,
                    repository = repository
                )

            }
        }
    }
}

