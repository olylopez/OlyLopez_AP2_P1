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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

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

    fun onSetTarea(tareaId: Int) {
        viewModelScope.launch {
            val tarea = repository.getTarea(tareaId)
            tarea?.let {
                uiState.update {
                    it.copy(
                        ticketId = tarea.ticketId,
                        descripcion = tarea.descripcion,
                        fecha = tarea.fecha,
                        precio = tarea.precio
                    )
                }
            }
        }
    }

    fun onDescripcionChanged(descripcion: String) {
        uiState.update {
            it.copy(descripcion = descripcion)
        }
    }
    fun onFechaChanged(fecha: String) {
        uiState.update {
            it.copy(fecha = fecha)
        }
    }
    fun onPrecioChanged(precio: String) {
        val letras = Regex("[a-zA-Z ]+")
        val numeros= precio.replace(letras, "").toDoubleOrNull()
        uiState.update {
            it.copy(precio = numeros)
        }
    }

    init {
        viewModelScope.launch {
            val tarea = repository.getTarea(tareaId)
            tarea?.let {
                uiState.update {
                    it.copy(
                        ticketId = tarea.ticketId,
                        descripcion = tarea.descripcion,
                        fecha = tarea.fecha,
                        precio = tarea.precio
                    )
                }
            }
        }
    }

    /*fun saveTarea(): Boolean {
        viewModelScope.launch {
            if(uiState.value.ticketId == null || uiState.value.ticketId == 0){
                repository.saveTarea(uiState.value.toDTO())
                uiState.value = TareasApiUIState()
            }
            else{
                repository.updateTarea(uiState.value.toDTO())
                uiState.value = TareasApiUIState()
            }
        }
        return true
    }*/
    fun saveTarea() {
        viewModelScope.launch {
            try {
                if (uiState.value.ticketId == null || uiState.value.ticketId == 0) {
                    repository.saveTarea(uiState.value.toDTO())
                } else {
                    repository.updateTarea(uiState.value.toDTO())
                }
                uiState.value = TareasApiUIState()
            } catch (e: Exception) {
                Log.e("ViewModel", "Error saving/updating task: ${e.localizedMessage}")
            }
        }
    }

    fun newTicket() {
        viewModelScope.launch {
            uiState.value = TareasApiUIState()
        }
    }
    fun deleteTicket(ticketId: Int) {
        viewModelScope.launch {
            try {
                repository.deleteTarea(TareasDto(ticketId, "", "", 0.0))
                getTareas()
            } catch (e: Exception) {
                Log.e("ViewModel", "Error deleting task: ${e.localizedMessage}")
            }
        }
    }

    fun validation(): Boolean {
        uiState.value.descripcionError = uiState.value.descripcion.isEmpty()
        uiState.value.precioError = (uiState.value.precio ?: 0.0) <= 0.0
        uiState.update {
            it.copy(
                saveSuccess = !uiState.value.descripcionError &&
                        !uiState.value.precioError
            )
        }
        return uiState.value.saveSuccess
    }




    fun getTareas() {
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
    }
}

data class TareasApiUIState(
    val ticketId: Int? = null,
    var descripcion: String = "",
    var fecha: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")),
    var precio: Double? = null,
    var saveSuccess: Boolean = false,
    var descripcionError: Boolean = false,
    var fechaError: Boolean = false,
    var precioError: Boolean = false,
    val tareas: List<TareasDto> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
fun TareasApiUIState.toDTO() = TareasDto(
    ticketId = ticketId ?: 0,
    descripcion = descripcion,
    fecha = fecha,
    precio = precio?: 0.0,

)
