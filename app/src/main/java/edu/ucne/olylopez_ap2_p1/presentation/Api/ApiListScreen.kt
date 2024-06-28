package edu.ucne.olylopez_ap2_p1.presentation.Api

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import edu.ucne.olylopez_ap2_p1.data.remote.dto.TareasDto
import edu.ucne.olylopez_ap2_p1.presentation.navigation.NavigationDrawer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApiListScreen(
    viewModel: TareaApiViewModel = hiltViewModel(),
    navController: NavHostController,
    onVerServicio: (TareasDto) -> Unit,
    onAddTarea: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
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
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(8.dp)
            ) {
                TareasListBory(
                    tareas = uiState.tareas,
                    onList = {
                        Log.d("ApiListScreen", "Fetching tasks from API")
                        viewModel.getTareas()
                    },
                    uistate = uiState,
                    onAddTarea = onAddTarea,
                    onVerTarea = onVerServicio,
                    onDeleteTarea = { ticketId -> viewModel.deleteTicket(ticketId) }
                    )
            }
        }

    }
}


    @Composable
    fun TareasListBory(
        tareas: List<TareasDto>,
        onList: () -> Unit,
        onVerTarea: (TareasDto) -> Unit,
        onDeleteTarea: (Int) -> Unit,
        onAddTarea: () -> Unit,
        uistate: TareasApiUIState,
    ) {

        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .padding(innerPadding)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { onList() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "load button"
                        )
                        Text("Cargar Datos(API)")
                    }


                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Id", modifier = Modifier.weight(0.10f))
                    Text(text = "DEscription", modifier = Modifier.weight(0.300f))
                    Text(text = "Fecha", modifier = Modifier.weight(0.30f))
                    Text(text = "Precio", modifier = Modifier.weight(0.30f))
                    IconButton(
                        onClick = { onAddTarea() },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Añadir button"
                            )
                        }
                    )
                }
                if (uistate.isLoading) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center

                    ) {
                        CircularProgressIndicator()
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
                                .clickable { onVerTarea(tarea) }
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = tarea.ticketId.toString(), modifier = Modifier.weight(0.10f))
                            Text(text = tarea.descripcion, modifier = Modifier.weight(0.300f))
                            Text(text = tarea.fecha, modifier = Modifier.weight(0.300f))
                            Text(text = tarea.precio.toString(), modifier = Modifier.weight(0.300f))
                            IconButton(
                                onClick = { onDeleteTarea(tarea.ticketId)},
                                content = {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "delete button"
                                    )
                                }
                            )

                        }
                    }
                }
            }
        }
    }


