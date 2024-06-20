package edu.ucne.olylopez_ap2_p1.presentation.Servicio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import edu.ucne.olylopez_ap2_p1.presentation.navigation.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicioScreen(
    viewModel: ServicioViewModel = hiltViewModel(),
    navController: NavHostController
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    viewModel.servicios.collectAsStateWithLifecycle()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Registro de Servicios",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(4.dp)
        ) {
            ServicioBory(
                uiState = uiState,
                onDescripcionChanged = viewModel::onDescripcionChanged,
                onPrecioChanged = viewModel::onPrecioChanged,
                onSaveServicio = { viewModel.saveServicio() },
                onNewServicio = { viewModel.newServicio() },
                onValidation = viewModel::validation,
                navController = navController
            )
        }
    }
}


@Composable
fun ServicioBory(
    uiState: ServicioUIState,
    onDescripcionChanged: (String) -> Unit,
    onPrecioChanged: (String) -> Unit,
    onSaveServicio: () -> Unit,
    onNewServicio: () -> Unit,
    onValidation: () -> Boolean,
    navController: NavHostController
){
    var clienteVacio by remember { mutableStateOf(false) }
    var descripcionVacia by remember { mutableStateOf(false) }
    var totalInvalido by remember { mutableStateOf(false) }
    var guardo by remember { mutableStateOf(false) }
    var errorGuardar by remember { mutableStateOf(false) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            ElevatedCard(
                modifier = Modifier
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {


                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        label = { Text(text = "Descripción") },
                        value = uiState.descripcion,
                        onValueChange = {
                            onDescripcionChanged(it)
                            descripcionVacia = it.isEmpty()
                        },
                        isError = descripcionVacia,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (descripcionVacia) {
                        Text(
                            text = "Campo Obligatorio",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        label = { Text(text = "Total") },
                        value = uiState.precio?.toString() ?: "",
                        onValueChange = {
                            onPrecioChanged(it)
                            totalInvalido = it.isEmpty() || it.toDoubleOrNull() == null || it.toDouble() <= 0
                        },
                        isError = totalInvalido,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (totalInvalido) {
                        Text(
                            text = "Debe ser un número mayor que 0",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp), // Añade padding horizontal
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {
                                onNewServicio()
                                descripcionVacia = false
                                totalInvalido = false
                                uiState.descripcion = ""
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "new button"
                            )
                            Text("Nuevo")
                        }
                        Button(
                            onClick = {
                                if (onValidation()) {
                                    onSaveServicio()
                                    guardo = true
                                    clienteVacio = false
                                    descripcionVacia = false
                                    totalInvalido = false
                                    navController.navigate(Screen.ServicioList)
                                } else {
                                    errorGuardar = true
                                    descripcionVacia = uiState.descripcionError
                                    totalInvalido = uiState.precioError
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(imageVector = Icons.Default.Person, contentDescription = "save")
                            Text(text = "Guardar")
                            /*Icon(
                                imageVector = Icons.Default.Person,
                                contentD1escription = "save button"
                            )
                            Text("Guardar")*/
                        }
                    }
                }
            }
        }
    }
}