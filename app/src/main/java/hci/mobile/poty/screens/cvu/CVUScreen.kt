package hci.mobile.poty.screens.cvu

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastCbrt
import hci.mobile.poty.R
import hci.mobile.poty.ui.components.BackButton
import hci.mobile.poty.ui.components.BottomNavBar
import hci.mobile.poty.ui.theme.PotyTheme
import hci.mobile.poty.ui.theme.White
import hci.mobile.poty.utils.WindowSizeClass
import hci.mobile.poty.utils.calculateWindowSizeClass

@Composable
fun CVUScreen(
    viewModel: CVUViewModel = CVUViewModel(),
    mockWindowSizeClass: WindowSizeClass? = null
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    val windowSizeClass = mockWindowSizeClass ?: calculateWindowSizeClass()
    val isLandscape = windowSizeClass == WindowSizeClass.MediumPhoneLandscape || windowSizeClass == WindowSizeClass.MediumTabletLandscape

    LaunchedEffect(Unit) {
        viewModel.fetchUserData()
    }

    PotyTheme(darkTheme = true, dynamicColor = false) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.secondary,
            bottomBar = { BottomNavBar(onNavigate = { }) }
        ) { innerPadding ->
            if (isLandscape) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    HeaderSection(
                        modifier = Modifier
                            .fillMaxHeight().fillMaxWidth(0.3f)
                            .weight(1f),
                    )
                    ContentSection(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .padding(horizontal = 15.dp),
                        state = state,
                        viewModel = viewModel,
                        context = context,
                        windowSizeClass = windowSizeClass
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
                        state = state,
                        viewModel = viewModel,
                        context = context,
                        windowSizeClass = windowSizeClass
                    )
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
                        text =  stringResource(id = R.string.account_details),
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
    state: CVUScreenState,
    viewModel: CVUViewModel,
    context: Context,
    windowSizeClass: WindowSizeClass
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

                DataTab(
                    key = stringResource(id = R.string.alias),
                    value = state.alias,
                    onCopyClick = { value ->
                        viewModel.copyToClipboard(context, value)
                    },
                    windowSizeClass = windowSizeClass,
                    firstRow = false
                )
                DataTab(
                    key = stringResource(id = R.string.email),
                    value = state.email,
                    onCopyClick = { value ->
                        viewModel.copyToClipboard(context, value)
                    },
                    windowSizeClass = windowSizeClass
                )
                DataTab(
                    key = stringResource(id = R.string.cbu),
                    value = state.cbu,
                    onCopyClick = { value ->
                        viewModel.copyToClipboard(context, value)
                    },
                    windowSizeClass = windowSizeClass
                )
            }
        }
    }
}

@Composable
fun DataTab(
    key: String,
    value: String,
    onCopyClick: (String) -> Unit,
    firstRow: Boolean = true,
    windowSizeClass: WindowSizeClass
) {
    val padding = when (windowSizeClass) {
        WindowSizeClass.MediumTablet, WindowSizeClass.MediumTabletLandscape -> 24.dp
        else -> 16.dp
    }

    if(firstRow){
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = padding),
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }


    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1.5f)
                .padding(15.dp)
        ) {
            Text(
                text = key,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color =  Color.Black
            )
        }

        IconButton(
            onClick = { onCopyClick(value) },
            modifier = Modifier
                .weight(0.5f)
                .size(80.dp)
                .align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Filled.ContentCopy,
                contentDescription = "Copiar",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}