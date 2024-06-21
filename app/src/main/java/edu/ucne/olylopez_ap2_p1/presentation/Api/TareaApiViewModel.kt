package edu.ucne.olylopez_ap2_p1.presentation.Api

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.olylopez_ap2_p1.data.remote.dto.TareasDto
import edu.ucne.olylopez_ap2_p1.repository.Resource
import edu.ucne.olylopez_ap2_p1.repository.TareaRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class TareaApiViewModel @Inject constructor(
    private val repository: TareaRepository
) : ViewModel() {
    private val tareaId: Int = 0
    var uiState = MutableStateFlow(TareasApiUIState())
        private set
    init {
        viewModelScope.launch {
            getTareas()
        }
    }
    fun getTareas() {
        viewModelScope.launch {
            try {
                uiState.update { it.copy(isLoading = true) }
                val result = repository.getTareas().first()
                Log.d("TareaApiViewModel", "API call result: $result")
                when (result) {
                    is Resource.Loading -> Log.d("TareaApiViewModel", "Loading")
                    is Resource.Success -> {
                        Log.d("TareaApiViewModel", "Success: ${result.data}")
                        uiState.update {
                            it.copy(
                                isLoading = false,
                                tareas = result.data ?: emptyList()
                            )
                        }
                    }
                    is Resource.Error -> {
                        Log.e("TareaApiViewModel", "Error: ${result.message}")
                        uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = result.message
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("TareaApiViewModel", "Exception: ${e.localizedMessage}", e)
                uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.localizedMessage
                    )
                }
            }
        }
    }



    /*fun getTareas() {
        viewModelScope.launch {
            repository.getTareas().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        Log.d("ViewModel", "Loading")
                        uiState.update { it.copy(isLoading = true) }
                        delay(2_000)
                    }
                    is Resource.Success -> {
                        Log.d("ViewModel", "Success")
                        uiState.update {
                            it.copy(
                                isLoading = false,
                                tareas = result.data ?: emptyList()
                            )
                        }
                    }
                    is Resource.Error -> {
                        Log.d("ViewModel", "Error: ${result.message}")
                        uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = result.message
                            )
                        }
                    }
                }
            }
        }
    }*/
}

data class TareasApiUIState(
    val tareaId: Int? = null,
    val empleadoId: Int? = null,
    var descripcion: String = "",
    var descripcionError: Boolean = false,
    var fecha: String = "",
    var fechaError: Boolean = false,
    var nombre: String = "",
    var nombreError: Boolean = false,
    var estado: String = "",
    var estadoError: Boolean = false,
    var codigoAcceso: String = "",
    var codigoAccesoError: Boolean = false,
    val tareas: List<TareasDto> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
fun TareasApiUIState.toEntity() = TareasDto(
    tareaId = tareaId ?: 0,
    empleadoId = empleadoId ?: 0,
    descripcion = descripcion,
    fecha = fecha,
    nombre = nombre,
    estado = estado,
    codigoAcceso = codigoAcceso,
)
