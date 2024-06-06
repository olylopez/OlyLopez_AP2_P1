package edu.ucne.olylopez_ap2_p1.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
fun AddButtom(onAddEntity: () -> Unit) {
    FloatingActionButton(
        onClick = onAddEntity
    ) {
        Icon(Icons.Filled.Add, "Agregar nueva entidad")
    }
}