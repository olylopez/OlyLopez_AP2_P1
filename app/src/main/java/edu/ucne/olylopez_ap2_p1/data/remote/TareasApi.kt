package edu.ucne.olylopez_ap2_p1.data.remote

import edu.ucne.olylopez_ap2_p1.data.remote.dto.TareasDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TareasApi {
    @GET("api/Tickets")
    suspend fun getTareas(): List<TareasDto>

    @GET("api/Tickets/{id}")
    suspend fun getTarea(@Path("id") id: Int): TareasDto

    @POST("api/Tickets")
    suspend fun saveTarea(@Body tareaDto: TareasDto?):TareasDto?
    @PUT("api/Tickets/{id}")
    suspend fun updateTarea(@Path("id") id: Int, @Body tareaDto: TareasDto?): Response<TareasDto>
    @DELETE("api/Tickets/{id}")
    suspend fun deleteTarea(@Path("id") id: Int): Response<Unit>


}