package edu.ucne.olylopez_ap2_p1.data.remote

import edu.ucne.olylopez_ap2_p1.data.remote.dto.TareasDto
import retrofit2.http.GET

interface TareasApi {
    @GET("api/Tareas")
    suspend fun getTareas(): List<TareasDto>
}