package pc02.moviles.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for Register Screen
 * Prepared for future Firebase integration
 */
class RegisterViewModel : ViewModel() {

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

    fun onCountryChange(country: String) {
        _uiState.update { it.copy(country = country, errorMessage = null) }
    }

    fun onStadiumChange(stadium: String) {
        _uiState.update { it.copy(stadium = stadium, errorMessage = null) }
    }

    /**
     * Register button click handler
     * TODO: Implement Firebase Firestore logic
     *
     * Future implementation:
     * 1. Validate input fields
     * 2. Create Team object
     * 3. Call repository.registerTeam()
     * 4. Handle success/error states
     * 5. Navigate to next screen on success
     */
    fun onRegisterClick() {
        viewModelScope.launch {
            // Validate fields
            if (!validateFields()) {
                return@launch
            }

            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            // TODO: Implement Firebase registration
            // val team = Team(
            //     name = _uiState.value.teamName,
            //     foundedYear = _uiState.value.foundedYear.toIntOrNull() ?: 0,
            //     country = _uiState.value.country,
            //     stadium = _uiState.value.stadium
            // )
            //
            // repository.registerTeam(team)
            //     .onSuccess { /* Navigate to next screen */ }
            //     .onFailure { error ->
            //         _uiState.update { it.copy(
            //             isLoading = false,
            //             errorMessage = error.message
            //         )}
            //     }

            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun validateFields(): Boolean {
        val state = _uiState.value

        when {
            state.teamName.isBlank() -> {
                _uiState.update { it.copy(errorMessage = "Team name is required") }
                return false
            }
            state.foundedYear.isBlank() -> {
                _uiState.update { it.copy(errorMessage = "Founded year is required") }
                return false
            }
            state.foundedYear.toIntOrNull() == null -> {
                _uiState.update { it.copy(errorMessage = "Invalid year") }
                return false
            }
            state.country.isBlank() -> {
                _uiState.update { it.copy(errorMessage = "Country is required") }
                return false
            }
            state.stadium.isBlank() -> {
                _uiState.update { it.copy(errorMessage = "Stadium is required") }
                return false
            }
        }

        return true
    }
}

/**
 * UI State for Register Screen
 */
data class RegisterUiState(
    val teamName: String = "",
    val foundedYear: String = "",
    val country: String = "",
    val stadium: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
