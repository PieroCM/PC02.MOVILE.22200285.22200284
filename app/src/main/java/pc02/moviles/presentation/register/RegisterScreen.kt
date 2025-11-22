package pc02.moviles.presentation.register

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Register Screen - Clover iOS UI Kit inspired design
 * Clean Architecture - UI Layer
 * Prepared for future Firebase integration
 */
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel(),
    onLoginClick: () -> Unit = {},
    onRegisterSuccess: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
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
        // Parallax mountains background layer
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
            RegisterContent(
                uiState = uiState,
                onTeamNameChange = viewModel::onTeamNameChange,
                onFoundedYearChange = viewModel::onFoundedYearChange,
                onCountryChange = viewModel::onCountryChange,
                onStadiumChange = viewModel::onStadiumChange,
                onRegisterClick = viewModel::onRegisterClick,
                onLoginClick = onLoginClick
            )
        }
    }
}

@Composable
private fun RegisterContent(
    uiState: RegisterUiState,
    onTeamNameChange: (String) -> Unit,
    onFoundedYearChange: (String) -> Unit,
    onCountryChange: (String) -> Unit,
    onStadiumChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit
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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 32.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        // Title
        Text(
            text = "CREATE TEAM",
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
            text = "Fill in your team details",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White.copy(alpha = 0.7f)
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Team Name Field
        TransparentTextField(
            value = uiState.teamName,
            onValueChange = onTeamNameChange,
            placeholder = "Team Name",
            enabled = !uiState.isLoading
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Founded Year Field
        TransparentTextField(
            value = uiState.foundedYear,
            onValueChange = onFoundedYearChange,
            placeholder = "Founded Year",
            keyboardType = KeyboardType.Number,
            enabled = !uiState.isLoading
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Country Field
        TransparentTextField(
            value = uiState.country,
            onValueChange = onCountryChange,
            placeholder = "Country",
            enabled = !uiState.isLoading
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Stadium Field
        TransparentTextField(
            value = uiState.stadium,
            onValueChange = onStadiumChange,
            placeholder = "Stadium",
            enabled = !uiState.isLoading
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Error Message
        if (uiState.errorMessage != null) {
            Text(
                text = uiState.errorMessage,
                style = TextStyle(
                    fontSize = 13.sp,
                    color = Color(0xFFFF6B6B),
                    fontWeight = FontWeight.Medium
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Sign Up Button
        OutlinedButton(
            onClick = onRegisterClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White
            ),
            border = androidx.compose.foundation.BorderStroke(2.dp, Color.White),
            enabled = !uiState.isLoading
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "SIGN UP",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.5.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Login Link
        TextButton(
            onClick = onLoginClick,
            enabled = !uiState.isLoading
        ) {
            Text(
                text = "Already have an account? ",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.7f),
                    fontWeight = FontWeight.Normal
                )
            )
            Text(
                text = "Login",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }

        Spacer(modifier = Modifier.height(60.dp))
    }
}

/**
 * Transparent TextField component with white underline
 * Matches Clover iOS UI Kit design
 */
@Composable
fun TransparentTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    enabled: Boolean = true
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                style = TextStyle(
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            )
        },
        modifier = modifier.fillMaxWidth(),
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f),
            disabledIndicatorColor = Color.White.copy(alpha = 0.3f),
            cursorColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            disabledTextColor = Color.White.copy(alpha = 0.5f)
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        enabled = enabled
    )
}

