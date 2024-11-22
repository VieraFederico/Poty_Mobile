package hci.mobile.poty.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import hci.mobile.poty.screens.addCard.AddCardScreen
import hci.mobile.poty.screens.dashboard.Dashboard
import hci.mobile.poty.screens.landing.LandingScreen
import hci.mobile.poty.screens.login.LoginScreen
import hci.mobile.poty.screens.register.RegistrationScreen
import hci.mobile.poty.screens.charge.ChargeScreen
import hci.mobile.poty.screens.deposit.DepositScreen

@Composable
fun AppNavGraph(navController: NavHostController, ) {
    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = Routes.LANDING // Ruta inicial
        ) {
            // Pantalla de bienvenida
            composable(route = Routes.LANDING) {
                LandingScreen(
                    onNavigateToLogin = { navController.navigate(Routes.LOGIN) },
                    onNavigateToRegister = { navController.navigate(Routes.REGISTER) }
                )
            }

            // Pantalla de login
            composable(route = Routes.LOGIN) {
                LoginScreen(
                    onLoginSuccess = { navController.navigate(Routes.DASHBOARD) },
                    onNavigateToRegister = { navController.navigate(Routes.REGISTER) }
                )
            }

            // Pantalla de registro
            composable(route = Routes.REGISTER) {
                RegistrationScreen(
                    onRegisterSuccess = { navController.navigate(Routes.DASHBOARD) },
                    onNavigateToLogin = { navController.navigate(Routes.LOGIN) }
                )
            }


            // Pantalla principal (Dashboard)
            composable(route = Routes.DASHBOARD) {
                Dashboard(
                    onNavigateToCharge = { navController.navigate(Routes.CHARGE_SCREEN) },
                    onNavigateToDeposit = { navController.navigate(Routes.DEPOSIT_SCREEN) },
                    onNavigateToAddCard = { navController.navigate(Routes.ADD_CARD_SCREEN) }
                )
            }

            composable(route = Routes.ADD_CARD_SCREEN) {
                AddCardScreen(
                    onNavigateToDashboard = { navController.navigate(Routes.DASHBOARD) },
                    onBackClick = {
                        val popped = navController.popBackStack()
                        println("Navigation" + "Backstack popped: $popped")
                        if (!popped) {
                            navController.navigate(Routes.DASHBOARD)
                        }
                    }
                )
            }

            composable(route = Routes.CHARGE_SCREEN) {
                ChargeScreen(

                )
            }

            composable(route = Routes.DEPOSIT_SCREEN) {
                DepositScreen(
                    onNavigateToAddCard = { navController.navigate(Routes.ADD_CARD_SCREEN) },
                    onNavigateToDashboard = { navController.navigate(Routes.DASHBOARD) }
                )
            }
        }
    }
}