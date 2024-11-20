package hci.mobile.poty.screens.addCard

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.window.layout.WindowMetricsCalculator
import hci.mobile.poty.R
import hci.mobile.poty.classes.CreditCard
import hci.mobile.poty.ui.components.BottomNavBar
import hci.mobile.poty.ui.components.FullCreditCardView
import hci.mobile.poty.ui.theme.GreenDark
import hci.mobile.poty.ui.theme.PotyTheme
import hci.mobile.poty.ui.theme.White
import hci.mobile.poty.ui.theme.titleSmallSemiBold
import hci.mobile.poty.utils.CompactDateFieldWithLabel
import hci.mobile.poty.utils.TextFieldWithLabel

// Enum for window size classes with specified names
enum class WindowSizeClass {
    MediumPhone,
    MediumPhoneLandscape,
    MediumTablet,
    MediumTabletLandscape
}

// Function to calculate window size class based on device dimensions
@Composable
fun calculateWindowSizeClass(
    mockWidthDp: Float? = null,
    mockHeightDp: Float? = null
): WindowSizeClass {
    val context = LocalContext.current
    val density = context.resources.displayMetrics.density

    val widthDp: Float
    val heightDp: Float

    if (mockWidthDp != null && mockHeightDp != null) {
        widthDp = mockWidthDp
        heightDp = mockHeightDp
    } else {
        val activity = context as Activity
        val windowMetrics = WindowMetricsCalculator.getOrCreate()
            .computeCurrentWindowMetrics(activity)
        widthDp = windowMetrics.bounds.width() / density
        heightDp = windowMetrics.bounds.height() / density
    }

    val isLandscape = widthDp > heightDp

    return when {
        // Medium Phone Portrait (approximate dp values)
        !isLandscape && widthDp in 400f..450f && heightDp in 850f..950f ->
            WindowSizeClass.MediumPhone

        // Medium Phone Landscape
        isLandscape && widthDp in 850f..950f && heightDp in 400f..450f ->
            WindowSizeClass.MediumPhoneLandscape

        // Medium Tablet Portrait
        !isLandscape && widthDp in 750f..850f && heightDp in 1200f..1350f ->
            WindowSizeClass.MediumTablet

        // Medium Tablet Landscape
        isLandscape && widthDp in 1200f..1350f && heightDp in 750f..850f ->
            WindowSizeClass.MediumTabletLandscape

        else -> {
            // Default to MediumPhone if size doesn't match
            if (isLandscape) WindowSizeClass.MediumPhoneLandscape
            else WindowSizeClass.MediumPhone
        }
    }
}

@Composable
fun AddCardScreen(
    viewModel: AddCardScreenViewModel = remember { AddCardScreenViewModel() },
    mockWindowSizeClass: WindowSizeClass? = null // Optional mock for Previews
) {
    val state by viewModel.state.collectAsState()

    val windowSizeClass = mockWindowSizeClass ?: calculateWindowSizeClass()

    // UI adjustments based on window size class
    val contentPadding: Dp
    val cardHeight: Dp
    val isLandscape: Boolean

    when (windowSizeClass) {
        WindowSizeClass.MediumPhone -> {
            contentPadding = 16.dp
            cardHeight = Dp.Unspecified
            isLandscape = false
        }
        WindowSizeClass.MediumPhoneLandscape -> {
            contentPadding = 16.dp
            cardHeight = Dp.Unspecified
            isLandscape = true
        }
        WindowSizeClass.MediumTablet -> {
            contentPadding = 32.dp
            cardHeight = Dp.Unspecified
            isLandscape = false
        }
        WindowSizeClass.MediumTabletLandscape -> {
            contentPadding = 32.dp
            cardHeight = Dp.Unspecified
            isLandscape = true
        }
    }

    PotyTheme(darkTheme = true, dynamicColor = false) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.secondary,
            bottomBar = { BottomNavBar { } },
        ) { innerPadding ->
            if (isLandscape) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .padding(contentPadding),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.background_scaffolding),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        IconButton(
                            onClick = { /* Handle navigation */ },
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
                            modifier = Modifier.fillMaxSize(),
                            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Text(
                                    text = "Agregar Nueva Tarjeta",
                                    style = MaterialTheme.typography.titleSmallSemiBold,
                                    color = White
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                if(windowSizeClass == WindowSizeClass.MediumPhoneLandscape) {
                                    FullCreditCardView(
                                        creditCard = CreditCard(
                                            bank = "Banco Royale",
                                            number = state.number,
                                            owner = state.owner,
                                            cvv = state.cvv,
                                            exp = state.exp
                                        )
                                    )
                                }
                            }
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.onBackground,
                        ),
                        shape = RoundedCornerShape(topStart = 30.dp, bottomStart = 30.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(contentPadding)
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {


                            if (windowSizeClass != WindowSizeClass.MediumPhoneLandscape) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .align(Alignment.CenterHorizontally)
                                ) {
                                    FullCreditCardView(
                                        creditCard = CreditCard(
                                            bank = "Banco Royale",
                                            number = state.number,
                                            owner = state.owner,
                                            cvv = state.cvv,
                                            exp = state.exp
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                            }



                            TextFieldWithLabel(
                                label = "Número de Tarjeta",
                                value = state.number,
                                onValueChange = { viewModel.onNumberChange(it) },
                                maxLength = 16,
                                regex = Regex("^\\d{0,16}\$")
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            TextFieldWithLabel(
                                label = "Nombre del Responsable",
                                value = state.owner,
                                onValueChange = { viewModel.onOwnerChange(it) }
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Column(modifier = Modifier.weight(1.5f)) {
                                    CompactDateFieldWithLabel(
                                        label = "Fecha de Venc. (MM/AA)",
                                        value = state.exp,
                                        onValueChange = { viewModel.onExpDateChange(it) }
                                    )
                                }

                                Column(modifier = Modifier.weight(1f)) {
                                    TextFieldWithLabel(
                                        label = "CVV",
                                        value = state.cvv,
                                        onValueChange = { viewModel.onCVVChange(it) },
                                        maxLength = 3
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = { viewModel.onAddCardClick() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .align(Alignment.CenterHorizontally),
                            ) {
                                Text(
                                    text = "Agregar Tarjeta",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }

                    }
                }
            } else {

                // Portrait layout
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.Top
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(contentPadding),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.background_scaffolding),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        IconButton(
                            onClick = { /* Handle navigation */ },
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
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Text(
                                    text = "Agregar Nueva Tarjeta",
                                    style = MaterialTheme.typography.titleSmallSemiBold,
                                    color = White
                                )
                            }
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(3.5f),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.onBackground,
                        ),
                        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(contentPadding)
                                .verticalScroll(rememberScrollState())
                        ) {

                            FullCreditCardView(
                                creditCard = CreditCard(
                                    bank = "Banco Royale",
                                    number = state.number,
                                    owner = state.owner,
                                    cvv = state.cvv,
                                    exp = state.exp
                                )
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            TextFieldWithLabel(
                                label = "Número de Tarjeta",
                                value = state.number,
                                onValueChange = { viewModel.onNumberChange(it) },
                                maxLength = 16,
                                regex = Regex("^\\d{0,16}\$")
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            TextFieldWithLabel(
                                label = "Nombre del Responsable",
                                value = state.owner,
                                onValueChange = { viewModel.onOwnerChange(it) }
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Row {
                                Column(modifier = Modifier.weight(1.5f)) {
                                    CompactDateFieldWithLabel(
                                        label = "Fecha de Venc. (MM/AA)",
                                        value = state.exp,
                                        onValueChange = { viewModel.onExpDateChange(it) },
                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    TextFieldWithLabel(
                                        label = "CVV",
                                        value = state.cvv,
                                        onValueChange = { viewModel.onCVVChange(it) },
                                        maxLength = 3,
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = { viewModel.onAddCardClick() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .align(Alignment.CenterHorizontally),
                            ) {
                                Text(
                                    text = "Agregar Tarjeta",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }
                }
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
fun MediumPhonePortraitPreview() {
    AddCardScreen(mockWindowSizeClass = WindowSizeClass.MediumPhone)
}

@Preview(
    name = "Medium Phone Landscape",
    device = "spec:width=914dp,height=411dp",
    showBackground = true
)
@Composable
fun MediumPhoneLandscapePreview() {
    AddCardScreen(mockWindowSizeClass = WindowSizeClass.MediumPhoneLandscape)
}

@Preview(
    name = "Medium Tablet Portrait",
    device = "spec:width=800dp,height=1280dp",
    showBackground = true
)
@Composable
fun MediumTabletPortraitPreview() {
    AddCardScreen(mockWindowSizeClass = WindowSizeClass.MediumTablet)
}

@Preview(
    name = "Medium Tablet Landscape",
    device = "spec:width=1280dp,height=800dp",
    showBackground = true
)
@Composable
fun MediumTabletLandscapePreview() {
    AddCardScreen(mockWindowSizeClass = WindowSizeClass.MediumTabletLandscape)
}
