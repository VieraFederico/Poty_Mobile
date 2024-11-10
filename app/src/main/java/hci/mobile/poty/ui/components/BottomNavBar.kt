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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hci.mobile.poty.R
import hci.mobile.poty.ui.theme.GreyLight
import hci.mobile.poty.ui.theme.PotyTheme
import hci.mobile.poty.ui.theme.White


@Composable
fun BottomNavBar(
    onNavigate: (String) -> Unit
) {
    NavigationBar (
        modifier = Modifier.height(70.dp),
        containerColor = GreyLight
    ){
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    modifier = Modifier.size(25.dp),
                    tint = Color.DarkGray
                )
            },
            selected = false,
            onClick = { onNavigate("notifications") }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Home",
                    modifier = Modifier.size(25.dp),
                    tint = Color.DarkGray
                )
            },
            selected = false,
            onClick = { onNavigate("home") }
        )

        NavigationBarItem(
            icon = {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(70.dp)  // Increased circle size
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.qr),
                        contentDescription = "QR",
                        tint = White,
                        modifier = Modifier.padding(10.dp)  // Decreased QR icon size
                    )
                }
            },
            selected = false,
            onClick = { onNavigate("qr_scanner") }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.trending_up),
                    contentDescription = "Inversiones",
                    tint = Color.DarkGray,
                    modifier = Modifier.size(25.dp)
                )
            },
            selected = false,
            onClick = { onNavigate("investments") }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Settings",
                    modifier = Modifier.size(25.dp),
                    tint = Color.DarkGray
                )
            },
            selected = false,
            onClick = { onNavigate("settings") }
        )
    }
}

@Preview
@Composable
fun BottomNavBarPreview(){
    PotyTheme { BottomNavBar {  } }

}