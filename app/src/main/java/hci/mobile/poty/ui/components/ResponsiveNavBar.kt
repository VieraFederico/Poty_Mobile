//package hci.mobile.poty.ui.components
//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Scaffold
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import hci.mobile.poty.utils.WindowSizeClass
//import hci.mobile.poty.utils.calculateWindowSizeClass
//
//@Composable
//fun ResponsiveNavBar(
//    onNavigate: (String) -> Unit,
//    mockWindowSizeClass: WindowSizeClass? = null,
//    content: @Composable () -> Unit
//) {
//    val windowSizeClass = mockWindowSizeClass ?: calculateWindowSizeClass()
//
//    val isLandscape = when (windowSizeClass) {
//        WindowSizeClass.MediumPhoneLandscape,
//        WindowSizeClass.MediumTabletLandscape -> true
//        else -> false
//    }
//
//    if (isLandscape) {
//        Row {
//            SideNavBar(
//                onNavigate = onNavigate,
//                mockWindowSizeClass = mockWindowSizeClass
//            )
//            // Content of the screen
//            Box(modifier = Modifier.fillMaxSize()) {
//                content()
//            }
//        }
//    } else {
//        // Use BottomNavBar
//        Scaffold(
//            bottomBar = {
//                BottomNavBar(onNavigate = onNavigate, mockWindowSizeClass = windowSizeClass)
//            },
//            content = { innerPadding ->
//                Box(modifier = Modifier.padding(innerPadding)) {
//                    content()
//                }
//            }
//        )
//    }

//}
package hci.mobile.poty.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hci.mobile.poty.utils.WindowSizeClass
import hci.mobile.poty.utils.calculateWindowSizeClass

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ResponsiveNavBar(
    onNavigate: (String) -> Unit,
    mockWindowSizeClass: WindowSizeClass? = null,
    content: @Composable () -> Unit
) {
    val windowSizeClass = mockWindowSizeClass ?: calculateWindowSizeClass()

    val isLandscape = when (windowSizeClass) {
        WindowSizeClass.MediumPhoneLandscape,
        WindowSizeClass.MediumTabletLandscape -> true
        else -> false
    }

    if (isLandscape) {
        Row {
            SideNavBar(
                onNavigate = onNavigate,
                mockWindowSizeClass = mockWindowSizeClass
            )
            // Content of the screen
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                content()
            }
        }
    } else {
        Scaffold(
            bottomBar = {
                BottomNavBar(onNavigate = onNavigate, mockWindowSizeClass = windowSizeClass)
            },
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize() // Avoid padding, manually handle the bottom bar space
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        // Your main content here
                        content()

                        Spacer(modifier = Modifier.height(100.dp)) // Reserve space for BottomNavBar
                    }
                }
            }
        )
    }
}
