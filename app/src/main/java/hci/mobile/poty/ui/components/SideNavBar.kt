//package hci.mobile.poty.ui.components
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material3.*
//import androidx.compose.material3.NavigationRail
//import androidx.compose.material3.NavigationRailItem
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import hci.mobile.poty.R
//import hci.mobile.poty.ui.theme.Black
//import hci.mobile.poty.ui.theme.GreyLight
//import hci.mobile.poty.ui.theme.PotyTheme
//import hci.mobile.poty.ui.theme.White
//import hci.mobile.poty.utils.WindowSizeClass
//import hci.mobile.poty.utils.calculateWindowSizeClass
//
//@Composable
//fun SideNavBar(
//    onNavigate: (String) -> Unit,
//    mockWindowSizeClass: WindowSizeClass? = null // Optional for previews
//) {
//    val windowSizeClass = mockWindowSizeClass ?: calculateWindowSizeClass()
//    val isTablet = when (windowSizeClass) {
//        WindowSizeClass.MediumTablet,
//        WindowSizeClass.MediumTabletLandscape -> true
//
//        else -> false
//    }
//
//    val navWidth = if (isTablet) 250.dp else 80.dp
//    val includeLabels = isTablet
//
//    NavigationRail(
//        modifier = Modifier
//            .width(navWidth)
//            .fillMaxHeight(),
//        containerColor = GreyLight,
//        contentColor = Color.Black
//    ) {
//        // Adding a Box to center the NavigationRail items for better use of vertical space
//        Box(
//            modifier = Modifier.fillMaxHeight(),
//            contentAlignment = Alignment.Center
//        ) {
//            Column(
//                modifier = Modifier.fillMaxHeight(),
//                verticalArrangement = Arrangement.SpaceEvenly, // Distribute items evenly
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                NavigationRailItem(
//                    icon = {
//                        Icon(
//                            Icons.Default.Notifications,
//                            contentDescription = "Notifications",
//                            modifier = Modifier.size(25.dp),
//                            tint = Color.DarkGray
//                        )
//                    },
//                    label = if (includeLabels) {
//                        { Text("Notifications") }
//                    } else null,
//                    selected = false,
//                    onClick = { onNavigate("notifications") }
//                )
//
//                NavigationRailItem(
//                    icon = {
//                        Icon(
//                            Icons.Default.Home,
//                            contentDescription = "Home",
//                            modifier = Modifier.size(25.dp),
//                            tint = Color.DarkGray
//                        )
//                    },
//                    label = if (includeLabels) {
//                        { Text("Home") }
//                    } else null,
//                    selected = false,
//                    onClick = { onNavigate("home") }
//                )
//
//                NavigationRailItem(
//                    icon = {
//                        Surface(
//                            shape = CircleShape,
//                            color = MaterialTheme.colorScheme.primary,
//                            modifier = Modifier.size(70.dp)
//                        ) {
//                            Icon(
//                                painter = painterResource(id = R.drawable.qr),
//                                contentDescription = "QR",
//                                tint = White,
//                                modifier = Modifier.padding(10.dp)
//                            )
//                        }
//                    },
//                    label = if (includeLabels) {
//                        { Text("QR Scanner") }
//                    } else null,
//                    selected = false,
//                    onClick = { onNavigate("qr_scanner") }
//                )
//
//                NavigationRailItem(
//                    icon = {
//                        Icon(
//                            painter = painterResource(id = R.drawable.trending_up),
//                            contentDescription = "Investments",
//                            tint = Color.DarkGray,
//                            modifier = Modifier.size(25.dp)
//                        )
//                    },
//                    label = if (includeLabels) {
//                        { Text("Investments") }
//                    } else null,
//                    selected = false,
//                    onClick = { onNavigate("investments") }
//                )
//
//                NavigationRailItem(
//                    icon = {
//                        Icon(
//                            Icons.Default.Settings,
//                            contentDescription = "Settings",
//                            modifier = Modifier.size(25.dp),
//                            tint = Color.DarkGray
//                        )
//                    },
//                    label = if (includeLabels) {
//                        { Text("Settings") }
//                    } else null,
//                    selected = false,
//                    onClick = { onNavigate("settings") }
//                )
//            }
//        }
//    }
//}
//


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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hci.mobile.poty.R
import hci.mobile.poty.ui.theme.Black
import hci.mobile.poty.ui.theme.GreyLight
import hci.mobile.poty.ui.theme.PotyTheme
import hci.mobile.poty.ui.theme.White
import hci.mobile.poty.utils.WindowSizeClass
import hci.mobile.poty.utils.calculateWindowSizeClass

@Composable
fun SideNavBar(
    onNavigate: (String) -> Unit,
    mockWindowSizeClass: WindowSizeClass? = null // Optional for previews
) {
    val windowSizeClass = mockWindowSizeClass ?: calculateWindowSizeClass()
    val isTablet = when (windowSizeClass) {
        WindowSizeClass.MediumTablet,
        WindowSizeClass.MediumTabletLandscape -> true

        else -> false
    }

    val navWidth = if (isTablet) 250.dp else 80.dp
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
                            Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            modifier = Modifier.size(25.dp),
                            tint = Color.DarkGray
                        )
                    },
                    label = if (includeLabels) {
                        { Text("Notifications", color = Color.Black) } // Text color updated
                    } else null,
                    selected = false,
                    onClick = { onNavigate("notifications") }
                )

                NavigationRailItem(
                    icon = {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Home",
                            modifier = Modifier.size(25.dp),
                            tint = Color.DarkGray
                        )
                    },
                    label = if (includeLabels) {
                        { Text("Home", color = Color.Black) } // Text color updated
                    } else null,
                    selected = false,
                    onClick = { onNavigate("home") }
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
                        { Text("QR Scanner", color = Color.Black) } // Text color updated
                    } else null,
                    selected = false,
                    onClick = { onNavigate("qr_scanner") }
                )

                NavigationRailItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.trending_up),
                            contentDescription = "Investments",
                            tint = Color.DarkGray,
                            modifier = Modifier.size(25.dp)
                        )
                    },
                    label = if (includeLabels) {
                        { Text("Investments", color = Color.Black) } // Text color updated
                    } else null,
                    selected = false,
                    onClick = { onNavigate("investments") }
                )

                NavigationRailItem(
                    icon = {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "Settings",
                            modifier = Modifier.size(25.dp),
                            tint = Color.DarkGray
                        )
                    },
                    label = if (includeLabels) {
                        { Text("Settings", color = Color.Black) } // Text color updated
                    } else null,
                    selected = false,
                    onClick = { onNavigate("settings") }
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun SideNavBarPreview() {
    PotyTheme {
        SideNavBar(onNavigate = { destination -> println("Navigated to $destination") }, mockWindowSizeClass = WindowSizeClass.MediumTablet)
    }
}