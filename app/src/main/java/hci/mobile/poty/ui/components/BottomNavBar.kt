package hci.mobile.poty.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hci.mobile.poty.R
import hci.mobile.poty.ui.theme.GreyDark
import hci.mobile.poty.navigation.LocalNavController
import hci.mobile.poty.navigation.Routes
import hci.mobile.poty.ui.theme.GreyLight
import hci.mobile.poty.ui.theme.PotyTheme
import hci.mobile.poty.ui.theme.White
import hci.mobile.poty.utils.WindowSizeClass
import hci.mobile.poty.utils.calculateWindowSizeClass


@Composable
fun BottomNavBar(
) {
    val windowSizeClass =  calculateWindowSizeClass()
    val navController = LocalNavController.current
    val isTablet = when (windowSizeClass) {
        WindowSizeClass.MediumTablet,
        WindowSizeClass.MediumTabletLandscape -> true
        else -> false
    }

    val newHeight = if (isTablet) 130.dp else 95.dp

    NavigationBar(
        modifier = Modifier.height(newHeight),
        containerColor = GreyLight
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Notifications,
                    contentDescription = stringResource(R.string.notifications),
                    modifier = Modifier.size(25.dp),
                    tint = GreyDark
                )
            },
            selected = false,
            onClick = {  }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = stringResource(R.string.home),
                    modifier = Modifier.size(25.dp),
                    tint = GreyDark
                )
            },
            selected = false,
            onClick = { navController.navigate(Routes.DASHBOARD) }
        )

        NavigationBarItem(
            icon = {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(70.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.qr),
                        contentDescription = "QR",
                        tint = White,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            },
            selected = false,
            onClick = {  }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.trending_up),
                    contentDescription = stringResource(R.string.investments),
                    tint = GreyDark,
                    modifier = Modifier.size(25.dp)
                )
            },
            selected = false,
            onClick = { }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = stringResource(R.string.settings),
                    modifier = Modifier.size(25.dp),
                    tint = GreyDark
                )
            },
            selected = false,
            onClick = {navController.navigate(Routes.SETTINGS_SCREEN) }
        )
    }
}
