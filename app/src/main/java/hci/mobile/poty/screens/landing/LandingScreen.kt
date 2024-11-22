package hci.mobile.poty.screens.landing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hci.mobile.poty.R
import hci.mobile.poty.ui.theme.GreenLight
import hci.mobile.poty.ui.theme.PotyTheme
import hci.mobile.poty.ui.theme.titleLargeItalic
import hci.mobile.poty.utils.WindowSizeClass
import hci.mobile.poty.utils.calculateWindowSizeClass
import hci.mobile.poty.utils.isLandscape
import hci.mobile.poty.utils.isTablet

@Composable
fun LandingScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit,
    mockWindowSizeClass: WindowSizeClass? = null
) {
    val windowSizeClass = mockWindowSizeClass ?: calculateWindowSizeClass()
    val isLandscape = windowSizeClass.isLandscape()
    val isTablet = windowSizeClass.isTablet()

    PotyTheme(darkTheme = true, dynamicColor = false) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background,
        ) { innerPadding ->
            if (isLandscape) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    LandingImageSection(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f),
                        windowSizeClass = windowSizeClass
                    )

                    LandingContentSection(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f),
                        onNavigateToLogin = onNavigateToLogin,
                        onNavigateToRegister = onNavigateToRegister,
                        windowSizeClass = windowSizeClass
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LandingImageSection(
                        modifier = Modifier
                            .fillMaxWidth(),
                        windowSizeClass = windowSizeClass
                    )

                    LandingContentSection(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onNavigateToLogin = onNavigateToLogin,
                        onNavigateToRegister = onNavigateToRegister,
                        windowSizeClass = windowSizeClass
                    )
                }
            }
        }
    }
}

@Composable
fun LandingImageSection(
    modifier: Modifier,
    windowSizeClass: WindowSizeClass
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.landing),
            contentDescription = "Foto Landing",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .padding( )
                .aspectRatio(1f)
        )
    }
}

@Composable
fun LandingContentSection(
    modifier: Modifier,
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit,
    windowSizeClass: WindowSizeClass
) {
    val contentPadding = if (windowSizeClass.isTablet()) 32.dp else 16.dp
    val textStyle = if (windowSizeClass.isTablet()) {
        MaterialTheme.typography.titleLarge
    } else {
        MaterialTheme.typography.titleMedium
    }

    val spacerHeight = if (windowSizeClass.isTablet()) 24.dp else 16.dp

    Column(
        modifier = modifier
            .padding(horizontal = contentPadding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(spacerHeight))

        Column(
            modifier = Modifier.padding(start = 15.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.with_Poty),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = textStyle
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.poty),
                    contentDescription = "Poty Logo",
                    modifier = Modifier.size(if (windowSizeClass.isTablet()) 40.dp else 24.dp)
                )
            }
            Text(
                text = stringResource(R.string.you_can_rest_easy),
                color = MaterialTheme.colorScheme.onBackground,
                style = textStyle
            )
            Text(
                text =  stringResource(R.string.your_money_is_in),
                color = MaterialTheme.colorScheme.onBackground,
                style = textStyle
            )
            Row {
                Text(
                    text =  stringResource(R.string.the_),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = textStyle
                )
                Text(
                    text =  stringResource(R.string.pot),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLargeItalic
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, bottom = 30.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                onClick = { onNavigateToLogin() },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color.White,
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp),
                shape = MaterialTheme.shapes.small.copy(all = CornerSize(10.dp)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text =  stringResource(R.string.log_in),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { onNavigateToRegister() },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = GreenLight,
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp),
                shape = MaterialTheme.shapes.small.copy(all = CornerSize(10.dp)),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text =  stringResource(R.string.sign_up),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Preview(
    name = "Medium Phone Portrait",
    device = "spec:width=411dp,height=914dp",
    showBackground = true
)
@Composable
fun LandingScreenMediumPhonePortraitPreview() {
    LandingScreen(
        onNavigateToLogin = {},
        onNavigateToRegister = {},
        mockWindowSizeClass = WindowSizeClass.MediumPhone
    )
}

@Preview(
    name = "Medium Phone Landscape",
    device = "spec:width=914dp,height=411dp",
    showBackground = true
)
@Composable
fun LandingScreenMediumPhoneLandscapePreview() {
    LandingScreen(
        onNavigateToLogin = {},
        onNavigateToRegister = {},
        mockWindowSizeClass = WindowSizeClass.MediumPhoneLandscape
    )
}

@Preview(
    name = "Medium Tablet Portrait",
    device = "spec:width=800dp,height=1280dp",
    showBackground = true
)
@Composable
fun LandingScreenMediumTabletPortraitPreview() {
    LandingScreen(
        onNavigateToLogin = {},
        onNavigateToRegister = {},
        mockWindowSizeClass = WindowSizeClass.MediumTablet
    )
}

@Preview(
    name = "Medium Tablet Landscape",
    device = "spec:width=1280dp,height=800dp",
    showBackground = true
)
@Composable
fun LandingScreenMediumTabletLandscapePreview() {
    LandingScreen(
        onNavigateToLogin = {},
        onNavigateToRegister = {},
        mockWindowSizeClass = WindowSizeClass.MediumTabletLandscape
    )
}

