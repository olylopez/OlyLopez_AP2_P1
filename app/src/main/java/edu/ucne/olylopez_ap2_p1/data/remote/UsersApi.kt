package edu.ucne.olylopez_ap2_p1.data.remote

import edu.ucne.olylopez_ap2_p1.data.remote.dto.UsersDto
import retrofit2.http.GET

interface UsersApi {
    @GET("api/Tareas")
    suspend fun getUsers(): List<UsersDto>
}