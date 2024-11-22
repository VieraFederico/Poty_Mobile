package hci.mobile.poty.navigation



import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

val LocalNavController = compositionLocalOf<NavHostController> {
    error("No NavHostController provided")
}

fun handleBackNavigation(
    navController: NavHostController,
    fallbackRoute: String = Routes.DASHBOARD
) {
    val popped = navController.popBackStack()
    if (!popped) {
        navController.navigate(fallbackRoute)
    }
}