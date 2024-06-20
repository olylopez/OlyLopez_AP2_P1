package edu.ucne.olylopez_ap2_p1.presentation.Api

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.olylopez_ap2_p1.data.remote.dto.TareasDto
import edu.ucne.olylopez_ap2_p1.data.remote.dto.UsersDto
import edu.ucne.olylopez_ap2_p1.repository.Resource
import edu.ucne.olylopez_ap2_p1.repository.TareaRepository
import edu.ucne.olylopez_ap2_p1.repository.UsersRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TareaApiViewModel @Inject constructor(
    private val taerasRepository: TareaRepository
) : ViewModel() {
    private val tareaId: Int = 0
    var uiState = MutableStateFlow(TareasApiUIState())
        private set

    suspend fun getTareas() {
        taerasRepository.getTareas().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    uiState.update { it.copy(isLoading = true) }
                    delay(2_000)
                }
                is Resource.Success -> {
                    uiState.update {
                        it.copy(
                            isLoading = false,
                            tareas = result.data ?: emptyList()
                        )
                    }
                }
                is Resource.Error -> {
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

data class TareasApiUIState(
    val tareaId: Int? = null,
    var descripcion: String = "",
    var descripcionError: Boolean = false,
    var nombre: String = "",
    var nombreError: Boolean = false,
    var codigoAcceso: String = "",
    var codigoAccesoError: Boolean = false,
    val tareas: List<TareasDto> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
fun TareasApiUIState.toEntity() = UsersDto(
    tareaId = tareaId ?: 0,
    descripcion = descripcion,
    nombre = nombre,
    codigoAcceso = codigoAcceso,
)
