
package hci.mobile.poty.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hci.mobile.poty.R
import hci.mobile.poty.data.repository.UserRepository
import hci.mobile.poty.navigation.LocalNavController
import hci.mobile.poty.navigation.Routes
import hci.mobile.poty.ui.theme.Black
import hci.mobile.poty.ui.theme.GreyLight
import hci.mobile.poty.ui.theme.PotyTheme
import hci.mobile.poty.ui.theme.White
import hci.mobile.poty.utils.WindowSizeClass
import hci.mobile.poty.utils.calculateWindowSizeClass
import hci.mobile.poty.utils.isTablet

@Composable
fun SideNavBar(
    onNavigate: (String) -> Unit,
    mockWindowSizeClass: WindowSizeClass? = null // Optional for previews
) {
    val windowSizeClass = mockWindowSizeClass ?: calculateWindowSizeClass()
    val isTablet = windowSizeClass.isTablet()

    val navController = LocalNavController.current

    val navWidth = if (isTablet) 200.dp else 80.dp
    val includeLabels = isTablet

    NavigationRail(
        modifier = Modifier
            .width(navWidth)
            .fillMaxHeight(),
        containerColor = GreyLight,
        contentColor = Color.Black // Updated content color to Black
    ) {
        // Adding a Box to center the NavigationRail items for better use of vertical space
        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly, // Distribute items evenly
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NavigationRailItem(
                    icon = {
                        Icon(
                            Icons.Default.Logout,
                            contentDescription = stringResource(R.string.notifications),
                            modifier = Modifier.size(25.dp),
                            tint = Color.DarkGray
                        )
                    },
                    label = if (includeLabels) {
                        { Text(stringResource(R.string.notifications), color = Color.Black) } // Text color updated
                    } else null,
                    selected = false,
                    onClick = {  }
                )

                NavigationRailItem(
                    icon = {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = stringResource(R.string.home),
                            modifier = Modifier.size(25.dp),
                            tint = Color.DarkGray
                        )
                    },
                    label = if (includeLabels) {
                        { Text(stringResource(R.string.home), color = Color.Black) } // Text color updated
                    } else null,
                    selected = false,
                    onClick = { navController.navigate(Routes.DASHBOARD) }
                )

                NavigationRailItem(
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
                    label = if (includeLabels) {
                        { Text(stringResource(R.string.qr_scanner), color = Color.Black) } // Text color updated
                    } else null,
                    selected = false,
                    onClick = { onNavigate("qr_scanner") }
                )

                NavigationRailItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.trending_up),
                            contentDescription = stringResource(R.string.investments),
                            tint = Color.DarkGray,
                            modifier = Modifier.size(25.dp)
                        )
                    },
                    label = if (includeLabels) {
                        { Text(stringResource(R.string.investments), color = Color.Black) } // Text color updated
                    } else null,
                    selected = false,
                    onClick = { onNavigate("investments") }
                )

                NavigationRailItem(
                    icon = {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = stringResource(R.string.settings),
                            modifier = Modifier.size(25.dp),
                            tint = Color.DarkGray
                        )
                    },
                    label = if (includeLabels) {
                        { Text(stringResource(R.string.settings), color = Color.Black) } // Text color updated
                    } else null,
                    selected = false,
                    onClick = { onNavigate("settings") }
                )
            }
        }
    }
}
