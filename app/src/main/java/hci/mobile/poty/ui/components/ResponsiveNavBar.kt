package hci.mobile.poty.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import hci.mobile.poty.utils.WindowSizeClass
import hci.mobile.poty.utils.calculateWindowSizeClass

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
            Box(modifier = Modifier.fillMaxSize()) {
                content()
            }
        }
    } else {
        // Use BottomNavBar
        Scaffold(
            bottomBar = {
                BottomNavBar(onNavigate = onNavigate)
            },
            content = { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    content()
                }
            }
        )
    }
}
