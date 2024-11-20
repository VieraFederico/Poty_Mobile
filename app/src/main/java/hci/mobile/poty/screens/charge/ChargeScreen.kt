package hci.mobile.poty.screens.charge

import ChargeScreenState
import ChargeScreenViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hci.mobile.poty.ui.theme.PotyTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import hci.mobile.poty.R
import hci.mobile.poty.ui.components.BottomNavBar
import hci.mobile.poty.ui.theme.GreenDark
import hci.mobile.poty.ui.theme.White
import hci.mobile.poty.ui.theme.titleSmallSemiBold
import hci.mobile.poty.utils.ErrorMessage
import hci.mobile.poty.utils.NumberFieldWithLabel
import hci.mobile.poty.utils.WindowSizeClass
import hci.mobile.poty.utils.calculateWindowSizeClass
import hci.mobile.poty.ui.components.ResponsiveNavBar
import hci.mobile.poty.utils.isLandscape
import hci.mobile.poty.utils.isTablet

@Composable
fun ChargeScreen(
    viewModel: ChargeScreenViewModel = remember { ChargeScreenViewModel() },
    onNavigateBack: () -> Unit = {},
    mockWindowSizeClass: WindowSizeClass? = null
) {
    val state by viewModel.state.collectAsState()
    val windowSizeClass = mockWindowSizeClass ?: calculateWindowSizeClass()

    val isLandscape = windowSizeClass.isLandscape()
    val contentPadding = if (windowSizeClass.isTablet()) 32.dp else 16.dp

    PotyTheme(darkTheme = true, dynamicColor = false) {
        ResponsiveNavBar(
            onNavigate = { /* Handle navigation */ },
            mockWindowSizeClass = mockWindowSizeClass
        ) {
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
                        ChargeHeaderSection(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f),
                            onNavigateBack = onNavigateBack,
                            contentPadding = contentPadding,
                            windowSizeClass = windowSizeClass
                        )

                        ChargeContentSection(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f),
                            state = state,
                            viewModel = viewModel,
                            contentPadding = contentPadding,
                            windowSizeClass = windowSizeClass
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        val headerWeight = if (windowSizeClass == WindowSizeClass.MediumTablet) {
                            1f
                        } else {
                            0.7f
                        }

                        ChargeHeaderSection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(headerWeight),
                            onNavigateBack = onNavigateBack,
                            contentPadding = contentPadding,
                            windowSizeClass = windowSizeClass
                        )

                        ChargeContentSection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(3.5f),
                            state = state,
                            viewModel = viewModel,
                            contentPadding = contentPadding,
                            windowSizeClass = windowSizeClass
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChargeHeaderSection(
    modifier: Modifier,
    onNavigateBack: () -> Unit,
    contentPadding: Dp,
    windowSizeClass: WindowSizeClass
) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_scaffolding),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        IconButton(
            onClick = { onNavigateBack() },
            modifier = Modifier.padding(contentPadding)
        ) {
            Surface(
                shape = CircleShape,
                color = GreenDark,
                modifier = Modifier.size(35.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Go Back",
                    tint = White
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxSize(),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
        ) {
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "Cobrar Dinero",
                    style = if (windowSizeClass.isTablet()) {
                        MaterialTheme.typography.titleLarge
                    } else {
                        MaterialTheme.typography.titleSmallSemiBold
                    },
                    color = White
                )
            }
        }
    }
}

@Composable
fun ChargeContentSection(
    modifier: Modifier,
    state: ChargeScreenState,
    viewModel: ChargeScreenViewModel,
    contentPadding: Dp,
    windowSizeClass: WindowSizeClass
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground,
        ),
        shape = RoundedCornerShape(
            topStart = if (!windowSizeClass.isLandscape()) 30.dp else 0.dp,
            topEnd = 30.dp,
            bottomEnd = if (windowSizeClass.isLandscape()) 0.dp else 0.dp,
            bottomStart = if (windowSizeClass.isLandscape()) 30.dp else 0.dp
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        when (state.currentStep) {
            1 -> StepOne(
                amount = state.amount,
                UpdateAmount = { viewModel.onEvent(ChargeScreenEvent.UpdateAmount(it)) },
                NextStep = { viewModel.onEvent(ChargeScreenEvent.NextStep) },
                errorMessage = state.errorMessage,
                contentPadding = contentPadding,
                windowSizeClass = windowSizeClass
            )
            2 -> StepTwo(
                contentPadding = contentPadding,
                windowSizeClass = windowSizeClass
            )
        }
    }
}

@Composable
fun StepOne(
    amount: String,
    UpdateAmount: (String) -> Unit,
    NextStep: () -> Unit,
    errorMessage: String,
    contentPadding: Dp,
    windowSizeClass: WindowSizeClass
) {
    Column(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NumberFieldWithLabel(
            label = "Monto a cobrar",
            value = amount.toFloatOrNull() ?: 0f,
            onValueChange = { newValue -> UpdateAmount(newValue.toString()) }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { NextStep() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Siguiente", color = MaterialTheme.colorScheme.onBackground)
        }
        if (errorMessage.isNotEmpty()) {
            ErrorMessage(message = errorMessage)
        }
    }
}

@Composable
fun StepTwo(
    contentPadding: Dp,
    windowSizeClass: WindowSizeClass
){
    Text(
        text = "STEP 2",
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(contentPadding)
    )
}



@Preview(
    name = "Medium Phone Portrait",
    device = "spec:width=411dp,height=914dp",
    showBackground = true
)
@Composable
fun ChargeScreenMediumPhonePortraitPreview() {
    ChargeScreen(
        onNavigateBack = {},
        mockWindowSizeClass = WindowSizeClass.MediumPhone
    )
}

@Preview(
    name = "Medium Phone Landscape",
    device = "spec:width=914dp,height=411dp",
    showBackground = true
)
@Composable
fun ChargeScreenMediumPhoneLandscapePreview() {
    ChargeScreen(
        onNavigateBack = {},
        mockWindowSizeClass = WindowSizeClass.MediumPhoneLandscape
    )
}

@Preview(
    name = "Medium Tablet Portrait",
    device = "spec:width=800dp,height=1280dp",
    showBackground = true
)
@Composable
fun ChargeScreenMediumTabletPortraitPreview() {
    ChargeScreen(
        onNavigateBack = {},
        mockWindowSizeClass = WindowSizeClass.MediumTablet
    )
}

@Preview(
    name = "Medium Tablet Landscape",
    device = "spec:width=1280dp,height=800dp",
    showBackground = true
)
@Composable
fun ChargeScreenMediumTabletLandscapePreview() {
    ChargeScreen(
        onNavigateBack = {},
        mockWindowSizeClass = WindowSizeClass.MediumTabletLandscape
    )
}
