package edu.ucne.olylopez_ap2_p1.presentation.Api

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import edu.ucne.olylopez_ap2_p1.presentation.Servicio.ServicioUIState
import edu.ucne.olylopez_ap2_p1.presentation.Servicio.ServicioViewModel
import edu.ucne.olylopez_ap2_p1.presentation.navigation.Screen
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApiScreen(
    viewModel: TareaApiViewModel = hiltViewModel(),
    navController: NavHostController,
    ticketId: Int?
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) {
        viewModel.onSetTarea(ticketId?:0)
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Registro de la API",
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
            ApiBory(
                uiState = uiState,
                onDescripcionChanged = viewModel::onDescripcionChanged,
                onFechaChanged = viewModel::onFechaChanged,
                onPrecioChanged = viewModel::onPrecioChanged,
                onSaveServicio = { viewModel.saveTarea() },
                onNewServicio = { viewModel.newTicket() },
                onValidation = viewModel::validation,
                navController = navController
            )
        }
    }
}

@Composable
fun ApiBory(
    uiState: TareasApiUIState,
    onDescripcionChanged: (String) -> Unit,
    onFechaChanged: (String) -> Unit,
    onPrecioChanged: (String) -> Unit,
    onSaveServicio: () -> Unit,
    onNewServicio: () -> Unit,
    onValidation: () -> Boolean,
    navController: NavHostController
){
    var showDatePicker by remember { mutableStateOf(false) }
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
                        label = { Text(text = "Fecha") },
                        value = uiState.fecha.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                        onValueChange = onFechaChanged,
                        readOnly = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    showDatePicker = true
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Date Picker"
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()

                            .clickable(enabled = true) {
                                showDatePicker = true
                            }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        label = { Text(text = "Total") },
                        value = uiState.precio?.toString() ?: "",
                        onValueChange = {
                            onPrecioChanged(it)
                            totalInvalido =
                                it.isEmpty() || it.toDoubleOrNull() == null || it.toDouble() <= 0
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
                                    navController.navigate(Screen.ApiList)
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
                        }
                    }
                }
            }
        }
    }
}