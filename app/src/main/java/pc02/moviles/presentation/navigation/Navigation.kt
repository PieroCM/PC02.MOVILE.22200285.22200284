package pc02.moviles.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pc02.moviles.presentation.futboll.ListadoScreen
import pc02.moviles.presentation.menu.MenuScreen
import pc02.moviles.presentation.register.RegisterScreen

/**
 * Navigation routes
 */
object Routes {
    const val MENU = "menu"
    const val REGISTER = "register"
    const val LISTADO = "listado"
}

/**
 * Main navigation component
 */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.MENU
    ) {
        // Menu Screen
        composable(Routes.MENU) {
            MenuScreen(
                onRegisterClick = {
                    navController.navigate(Routes.REGISTER)
                },
                onListadoClick = {
                    navController.navigate(Routes.LISTADO)
                }
            )
        }

        // Register Screen
        composable(Routes.REGISTER) {
            RegisterScreen(
                onLoginClick = {
                    // TODO: Navigate to Login when implemented
                },
                onRegisterSuccess = {
                    // Navigate to Listado after successful registration
                    navController.navigate(Routes.LISTADO) {
                        popUpTo(Routes.MENU)
                    }
                }
            )
        }

        // Listado Screen
        composable(Routes.LISTADO) {
            ListadoScreen(
                onNuevoRegistroClick = {
                    navController.navigate(Routes.REGISTER)
                }
            )
        }
    }
}

