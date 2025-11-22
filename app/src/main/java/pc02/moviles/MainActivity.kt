package pc02.moviles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import pc02.moviles.presentation.futboll.ListadoScreen
import pc02.moviles.ui.theme.PC02MovilesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PC02MovilesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ListadoScreen(
                        onNuevoRegistroClick = {
                            // TODO: Navegar a la pantalla de registro
                            // Por ahora solo un placeholder
                        }
                    )
                }
            }
        }
    }
}