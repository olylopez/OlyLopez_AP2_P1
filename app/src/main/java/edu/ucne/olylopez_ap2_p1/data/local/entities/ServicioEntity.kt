package edu.ucne.olylopez_ap2_p1.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "Servicios")
data class ServicioEntity(
    @PrimaryKey
    val servicioId: Int? = null,
    val descripcion: String? = "",
    val precio: Double? = null
)