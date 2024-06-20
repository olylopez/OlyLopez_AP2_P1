package edu.ucne.olylopez_ap2_p1.presentation.Api

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.ucne.olylopez_ap2_p1.data.remote.dto.TareasDto
import edu.ucne.olylopez_ap2_p1.presentation.navigation.NavigationDrawer

@Composable
fun ApiListScreen(
    navController: NavHostController,
    tareas: List<TareasDto>
){

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TareasListBory(
    navController: NavHostController,
    tareas: List<TareasDto>)
{


        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        NavigationDrawer(navController = navController, drawerState = drawerState) {

            Scaffold(modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                "Lista de la API",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    )
                }
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {

                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "new button"
                            )
                            Text("Cargar Datos(API)")
                        }
                    }
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(tareas) { tarea ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = tarea.tareaId.toString(), modifier = Modifier.weight(0.10f))
                        Text(text = tarea.descripcion, modifier = Modifier.weight(0.300f))
                        Text(text = tarea.nombre, modifier = Modifier.weight(0.300f))
                        Text(text = tarea.codigoAcceso, modifier = Modifier.weight(0.300f))

                    }
                }
            }
        }
    }
