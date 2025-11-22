package pc02.moviles.presentation.futboll

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pc02.moviles.data.model.Equipo
import pc02.moviles.data.repository.EquipoRepository

data class ListadoUiState(
    val isLoading: Boolean = false,
    val equipos: List<Equipo> = emptyList(),
    val errorMessage: String? = null
)

class ListadoViewModel(
    private val repository: EquipoRepository = EquipoRepository()
) : ViewModel() {

    var uiState by mutableStateOf(ListadoUiState())
        private set

    init {
        cargarEquipos()
    }

    fun cargarEquipos() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)

            repository.obtenerEquipos()
                .onSuccess { equipos ->
                    uiState = uiState.copy(
                        isLoading = false,
                        equipos = equipos
                    )
                }
                .onFailure { exception ->
                    uiState = uiState.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Error desconocido al cargar equipos"
                    )
                }
        }
    }
}

