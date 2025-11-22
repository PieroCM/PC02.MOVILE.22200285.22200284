package pc02.moviles.presentation.futboll

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF4527A0), // Deep Purple
                        Color(0xFF6A1B9A), // Purple
                        Color(0xFFD81B60)  // Pink
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(0f, Float.POSITIVE_INFINITY)
                )
            )
    ) {
        // Parallax background layer
        Box(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.35f)
                .background(Color.Black.copy(alpha = 0.1f))
        )

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(
                animationSpec = tween(durationMillis = 1200, easing = FastOutSlowInEasing)
            )
        ) {
            ListadoContent(
                state = state,
                onCargarEquipos = { viewModel.cargarEquipos() },
                onNuevoRegistroClick = onNuevoRegistroClick
            )
        }
    }
}

@Composable
private fun ListadoContent(
    state: ListadoUiState,
    onCargarEquipos: () -> Unit,
    onNuevoRegistroClick: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "scale")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título
        Text(
            text = "EQUIPOS",
            style = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                letterSpacing = 2.sp
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Liga 1 Perú",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White.copy(alpha = 0.7f)
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

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
                        onRetry = onCargarEquipos
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

        Spacer(modifier = Modifier.height(24.dp))

        // Botón Nuevo Registro con el mismo estilo
        OutlinedButton(
            onClick = onNuevoRegistroClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White
            ),
            border = androidx.compose.foundation.BorderStroke(2.dp, Color.White)
        ) {
            Text(
                text = "NUEVO REGISTRO",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.5.sp
                )
            )
        }
    }
}
