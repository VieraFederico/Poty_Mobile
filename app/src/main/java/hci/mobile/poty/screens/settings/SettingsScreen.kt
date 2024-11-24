import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastCbrt
import androidx.lifecycle.viewmodel.compose.viewModel
import hci.mobile.poty.MyApplication
import hci.mobile.poty.R
import hci.mobile.poty.screens.addCard.AddCardScreenViewModel
import hci.mobile.poty.screens.cvu.CVUViewModel
import hci.mobile.poty.screens.settings.SettingsViewModel
import hci.mobile.poty.ui.components.BackButton
import hci.mobile.poty.ui.components.BottomNavBar
import hci.mobile.poty.ui.components.ResponsiveNavBar
import hci.mobile.poty.ui.theme.PotyTheme
import hci.mobile.poty.ui.theme.White
import hci.mobile.poty.utils.WindowSizeClass
import hci.mobile.poty.utils.calculateWindowSizeClass

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = viewModel(
        factory = SettingsViewModel.provideFactory(
            LocalContext.current.applicationContext as MyApplication
        )
    ),
    mockWindowSizeClass: WindowSizeClass? = null,
    onNavigateToLanding: () -> Unit,
    ) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    val windowSizeClass = mockWindowSizeClass ?: calculateWindowSizeClass()
    val isLandscape = windowSizeClass == WindowSizeClass.MediumPhoneLandscape || windowSizeClass == WindowSizeClass.MediumTabletLandscape

    PotyTheme() {
        ResponsiveNavBar {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = MaterialTheme.colorScheme.secondary,

            ) { innerPadding ->
                if (isLandscape) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        HeaderSection(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(0.5f),
                        )
                        ContentSection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(horizontal = 15.dp),
                            context = context,
                            windowSizeClass = windowSizeClass,
                            viewModel = viewModel,
                            onNavigateToLanding = onNavigateToLanding

                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        HeaderSection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.3f),
                        )
                        ContentSection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            context = context,
                            windowSizeClass = windowSizeClass,
                            viewModel = viewModel,
                            onNavigateToLanding = onNavigateToLanding

                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderSection(
    modifier: Modifier,
    contentPadding: Dp = 16.dp,
) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_scaffolding),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            BackButton()
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(contentPadding),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text =  stringResource(id = R.string.settings),
                        style = MaterialTheme.typography.titleMedium,
                        color = White
                    )
                }
            }
        }
    }
}



@Composable
fun ContentSection(
    modifier: Modifier,
    context: Context,
    windowSizeClass: WindowSizeClass,
    viewModel: SettingsViewModel,
    onNavigateToLanding: () -> Unit

) {
    val spacerHeight = when (windowSizeClass) {
        WindowSizeClass.MediumPhone -> 50.dp
        WindowSizeClass.MediumPhoneLandscape -> 20.dp
        WindowSizeClass.MediumTablet -> 100.dp
        WindowSizeClass.MediumTabletLandscape -> 50.dp
        else -> 50.dp
    }

    val cardPadding = if (windowSizeClass == WindowSizeClass.MediumPhone ||
        windowSizeClass == WindowSizeClass.MediumTablet
    ) {
        Modifier.padding(horizontal = 16.dp)
    } else {
        Modifier
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(spacerHeight))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .then(cardPadding),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onBackground,
            ),
            shape = RoundedCornerShape(30.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SettingTab(
                    key = stringResource(id = R.string.account_details),
                    onSettingsClick = { viewModel.onLogOut(onNavigateToLanding) },
                    windowSizeClass = windowSizeClass,
                    notFirstRow = false,
                    icon = Icons.Filled.Person,
                )
                SettingTab(
                    key = stringResource(id = R.string.card),
                    onSettingsClick = { viewModel.onLogOut(onNavigateToLanding) },
                    windowSizeClass = windowSizeClass,
                    notFirstRow = true,
                    icon = Icons.Filled.CreditCard,
                )
                SettingTab(
                    key = stringResource(id = R.string.notifications),
                    onSettingsClick = { viewModel.onLogOut(onNavigateToLanding) },
                    windowSizeClass = windowSizeClass,
                    notFirstRow = true,
                    icon = Icons.Filled.Notifications,
                )

                SettingTab(
                    key = stringResource(id = R.string.log_out),
                    onSettingsClick = { viewModel.onLogOut(onNavigateToLanding) },
                    windowSizeClass = windowSizeClass,
                    notFirstRow = true,
                    icon = Icons.Filled.Logout,
                )
            }
        }
    }
}

@Composable
fun SettingTab(
    key: String,
    onSettingsClick: () -> Unit,
    notFirstRow: Boolean = true,
    windowSizeClass: WindowSizeClass,
    icon: ImageVector,
) {
    val padding = when (windowSizeClass) {
        WindowSizeClass.MediumTablet, WindowSizeClass.MediumTabletLandscape -> 24.dp
        else -> 16.dp
    }

    if (notFirstRow) {
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = padding),
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = padding, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Action",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(40.dp)
                .clickable(onClick = onSettingsClick)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = key,
            style = MaterialTheme.typography.labelLarge,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}
