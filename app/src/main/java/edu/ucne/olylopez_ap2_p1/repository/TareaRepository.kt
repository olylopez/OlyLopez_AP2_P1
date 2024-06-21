package edu.ucne.olylopez_ap2_p1.repository

import android.util.Log
import edu.ucne.olylopez_ap2_p1.data.remote.TareasApi
import edu.ucne.olylopez_ap2_p1.data.remote.dto.TareasDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TareaRepository @Inject constructor(
    private val tareasApi: TareasApi
) {
    suspend fun getTareas(): Flow<Resource<List<TareasDto>>> = flow {
        emit(Resource.Loading())
        try {
            val tareas = tareasApi.getTareas()
            Log.d("TareaRepository", "API response: $tareas")
            emit(Resource.Success(tareas))
        } catch (e: Exception) {
            Log.e("TareaRepository", "API call failed: ${e.localizedMessage}", e)
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}