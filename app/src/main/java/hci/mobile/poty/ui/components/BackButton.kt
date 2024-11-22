package hci.mobile.poty.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import hci.mobile.poty.navigation.LocalNavController
import hci.mobile.poty.navigation.handleBackNavigation
import hci.mobile.poty.ui.theme.GreenDark
import hci.mobile.poty.ui.theme.White

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    fallbackRoute: String = "dashboard"
) {
    val navController = LocalNavController.current

    IconButton(
        onClick = { handleBackNavigation(navController, fallbackRoute) },
        modifier = modifier
            .zIndex(1f)
    ) {
        Surface(
            shape = CircleShape,
            color = GreenDark,
            modifier = Modifier.size(35.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, // Use left arrow icon
                contentDescription = "Go Back",
                tint = White // Icon color
            )
        }
    }
}
