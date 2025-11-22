package pc02.moviles.presentation.register

/**
 * UI State for Register Screen
 */
data class RegisterUiState(
    val teamName: String = "",
    val foundedYear: String = "",
    val country: String = "",
    val stadium: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val registerSuccess: Boolean = false
)

