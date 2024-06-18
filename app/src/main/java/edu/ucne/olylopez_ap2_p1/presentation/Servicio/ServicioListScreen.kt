package edu.ucne.olylopez_ap2_p1.presentation.Servicio

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.ucne.olylopez_ap2_p1.data.local.entities.ServicioEntity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.olylopez_ap2_p1.presentation.navigation.AddButtom

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicioListScreen(
    viewModel: ServicioViewModel,
    onVerServicio: (ServicioEntity) -> Unit,
    onAddServicio: () -> Unit,
    navController: NavHostController
) {
    val servicios by viewModel.servicios.collectAsStateWithLifecycle()
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text("Lista de Servicios",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center)
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
            ServicioTecListBody(
                servicio = servicios,
                onAddServicio = onAddServicio,
                onVerServicio = onVerServicio,
                onDeleteServicio = { servicio -> viewModel.deleteServicio(servicio) }
            )

        }
    }
}


@Composable
fun ServicioTecListBody(
    servicio: List<ServicioEntity>,
    onAddServicio: () -> Unit,
    onDeleteServicio: (ServicioEntity) -> Unit,
    onVerServicio: (ServicioEntity) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)

    ) {
        ElevatedCard(
                modifier = Modifier
                ) {
            Row(
                modifier = Modifier.padding(8.dp)
            ) {

                Text(
                    text = "Descripción",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.weight(0.4f)
                )


                Text(
                    text = "Precio",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.weight(0.1f)
                )
                IconButton(
                    onClick = { onAddServicio() },
                    content = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Añadir button"
                        )
                    }
                )

            }
    }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            items(servicio) { servicio ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { onVerServicio(servicio) }
                        .background(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp)
                ) {
                    Text(text = servicio.descripcion ?: "", modifier = Modifier.weight(0.4f))
                    Text(text = servicio.precio?.toString() ?: "", modifier = Modifier.weight(0.1f))

                    IconButton(
                        onClick = { onDeleteServicio(servicio) },
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