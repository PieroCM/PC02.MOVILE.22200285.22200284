package pc02.moviles.presentation.futboll

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import pc02.moviles.presentation.futbollcomponents.EquiposList
import pc02.moviles.presentation.futbollcomponents.ErrorView
import pc02.moviles.presentation.futbollcomponents.LoadingView

@Composable
fun ListadoScreen(
    viewModel: ListadoViewModel = viewModel(),
    onNuevoRegistroClick: () -> Unit
) {
    val state = viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Título
        Text(
            text = "Equipos",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Contenido principal
        Box(
            modifier = Modifier.weight(1f)
        ) {
            when {
                state.isLoading -> {
                    LoadingView()
                }
                state.errorMessage != null -> {
                    ErrorView(
                        message = state.errorMessage,
                        onRetry = { viewModel.cargarEquipos() }
                    )
                }
                else -> {
                    EquiposList(
                        equipos = state.equipos,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón Nuevo Registro
        Button(
            onClick = onNuevoRegistroClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Nuevo Registro")
        }
    }
}
