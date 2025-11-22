package pc02.moviles.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pc02.moviles.data.model.Equipo
import pc02.moviles.data.repository.EquipoRepository

/**
 * ViewModel for Register Screen
 * Complete Firebase integration with Firestore
 */
class RegisterViewModel(
    private val repository: EquipoRepository = EquipoRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onTeamNameChange(name: String) {
        _uiState.update { it.copy(teamName = name, errorMessage = null) }
    }

    fun onFoundedYearChange(year: String) {
        // Only allow digits
        if (year.isEmpty() || year.all { it.isDigit() }) {
            _uiState.update { it.copy(foundedYear = year, errorMessage = null) }
        }
    }

    fun onTitulosGanadosChange(titulos: String) {
        // Only allow digits
        if (titulos.isEmpty() || titulos.all { it.isDigit() }) {
            _uiState.update { it.copy(titulosGanados = titulos, errorMessage = null) }
        }
    }

    fun onImagenUrlChange(url: String) {
        _uiState.update { it.copy(imagenUrl = url, errorMessage = null) }
    }

    /**
     * Register button click handler
     * Validates input and registers team to Firestore
     */
    fun onRegisterClick() {
        viewModelScope.launch {
            // Validate fields
            if (!validateFields()) {
                return@launch
            }

            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            // Create Equipo object
            val equipo = Equipo(
                id = "", // Firestore will generate the ID
                nombreEquipo = _uiState.value.teamName,
                anioFundacion = _uiState.value.foundedYear.toIntOrNull() ?: 0,
                titulosGanados = _uiState.value.titulosGanados.toIntOrNull() ?: 0,
                imagenUrl = _uiState.value.imagenUrl
            )

            // Call repository to register team
            repository.registrarEquipo(equipo)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            registerSuccess = true,
                            errorMessage = null
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            registerSuccess = false,
                            errorMessage = error.message ?: "Error al registrar el equipo"
                        )
                    }
                }
        }
    }

    /**
     * Validates all input fields
     * Returns true if all fields are valid
     */
    private fun validateFields(): Boolean {
        val state = _uiState.value

        when {
            state.teamName.isBlank() -> {
                _uiState.update { it.copy(errorMessage = "El nombre del equipo es requerido") }
                return false
            }
            state.foundedYear.isBlank() -> {
                _uiState.update { it.copy(errorMessage = "El año de fundación es requerido") }
                return false
            }
            state.foundedYear.toIntOrNull() == null -> {
                _uiState.update { it.copy(errorMessage = "El año de fundación debe ser un número válido") }
                return false
            }
            state.titulosGanados.isBlank() -> {
                _uiState.update { it.copy(errorMessage = "Los títulos ganados son requeridos") }
                return false
            }
            state.titulosGanados.toIntOrNull() == null -> {
                _uiState.update { it.copy(errorMessage = "Los títulos ganados deben ser un número válido") }
                return false
            }
        }

        return true
    }

    /**
     * Reset register success state
     * Call this after navigation to reset the flag
     */
    fun resetRegisterSuccess() {
        _uiState.update { it.copy(registerSuccess = false) }
    }
}
