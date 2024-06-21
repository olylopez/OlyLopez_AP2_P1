package edu.ucne.olylopez_ap2_p1.data.remote.dto

class TareasDto (
    var tareaId: Int,
    var empleadoId: Int,
    val descripcion: String,
    val fecha: String,
    val nombre: String,
    val estado: String,
    val codigoAcceso: String,
)