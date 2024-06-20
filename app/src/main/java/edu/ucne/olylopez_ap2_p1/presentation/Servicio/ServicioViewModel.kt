package edu.ucne.olylopez_ap2_p1.presentation.Servicio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.olylopez_ap2_p1.data.local.entities.ServicioEntity
import edu.ucne.olylopez_ap2_p1.repository.ServicioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServicioViewModel @Inject constructor(
    private val repository: ServicioRepository,
    ) : ViewModel() {
    private val servicioId: Int = 0
    var uiState = MutableStateFlow(ServicioUIState())
        private set

    val servicios = repository.getServicio()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
    init {
        viewModelScope.launch {
            val servicio = repository.getServicio(servicioId)
            servicio?.let {
                uiState.update {
                    it.copy(
                        servicioId = servicio.servicioId,
                        descripcion = servicio.descripcion ?: "",
                        precio = servicio.precio
                    )
                }
            }
        }
    }

    fun deleteServicio(servicio: ServicioEntity) {
        viewModelScope.launch {
            servicio.servicioId?.let { repository.deleteById(it) }
        }
    }

    fun onDescripcionChanged(descripcion: String) {
        uiState.update {
            it.copy(descripcion = descripcion)
        }
    }

    fun onPrecioChanged(totalStr: String) {
        val regex = Regex("[0-9]{0,7}\\.?[0-9]{0,2}")
        if (totalStr.matches(regex)) {
            val precio = totalStr.toDoubleOrNull() ?: 0.0
            uiState.update {
                it.copy(precio = precio)
            }
        }
    }




    fun saveServicio() {
        viewModelScope.launch {
            repository.saveServicio(uiState.value.toEntity())
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

    fun newServicio() {
        viewModelScope.launch {
            uiState.value = ServicioUIState()
        }
    }
}

data class ServicioUIState(
    val servicioId: Int? = null,
    var descripcion: String = "",
    var descripcionError: Boolean = false,
    var precio: Double? = null,
    var precioError: Boolean = false,
    var saveSuccess: Boolean = false

)

fun ServicioUIState.toEntity(): ServicioEntity {
    return ServicioEntity(
        servicioId = servicioId,
        descripcion = descripcion,
        precio = precio
    )
}