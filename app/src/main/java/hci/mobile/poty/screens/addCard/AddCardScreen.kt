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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.window.layout.WindowMetricsCalculator
import hci.mobile.poty.R
import hci.mobile.poty.classes.CreditCard
import hci.mobile.poty.ui.components.BottomNavBar
import hci.mobile.poty.ui.components.FullCreditCardView
import hci.mobile.poty.ui.components.ResponsiveNavBar
import hci.mobile.poty.ui.theme.GreenDark
import hci.mobile.poty.ui.theme.PotyTheme
import hci.mobile.poty.ui.theme.White
import hci.mobile.poty.ui.theme.titleSmallSemiBold
import hci.mobile.poty.utils.CompactDateFieldWithLabel
import hci.mobile.poty.utils.TextFieldWithLabel
import hci.mobile.poty.utils.WindowSizeClass
import hci.mobile.poty.utils.calculateWindowSizeClass



@Composable
fun AddCardScreen(
    viewModel: AddCardScreenViewModel = remember { AddCardScreenViewModel() },
    mockWindowSizeClass: WindowSizeClass? = null,
    onNavigateToDashboard: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    viewModel.onAddCardSuccess = onNavigateToDashboard
    val windowSizeClass = mockWindowSizeClass ?: calculateWindowSizeClass()

    val contentPadding = if (windowSizeClass == WindowSizeClass.MediumTablet || windowSizeClass == WindowSizeClass.MediumTabletLandscape) 32.dp else 16.dp
    val isLandscape = windowSizeClass == WindowSizeClass.MediumPhoneLandscape || windowSizeClass == WindowSizeClass.MediumTabletLandscape

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
                            .padding(innerPadding),
                    ) {
                        HeaderSection(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f),
                            contentPadding = contentPadding,
                            showCreditCard = windowSizeClass == WindowSizeClass.MediumPhoneLandscape,
                            state = state,
                            onBackClick = { /* Handle navigation */ }
                        )

                        FormSection(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f),
                            contentPadding = contentPadding,
                            showCreditCard = windowSizeClass != WindowSizeClass.MediumPhoneLandscape,
                            state = state,
                            onNumberChange = viewModel::onNumberChange,
                            onOwnerChange = viewModel::onOwnerChange,
                            onExpDateChange = viewModel::onExpDateChange,
                            onCVVChange = viewModel::onCVVChange,
                            onAddCardClick = viewModel::onAddCardClick,
                            shape = RoundedCornerShape(topStart = 30.dp, bottomStart = 30.dp)
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Top
                    ) {
                        HeaderSection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            contentPadding = contentPadding,
                            showCreditCard = false,
                            state = state,
                            onBackClick = { /* Handle navigation */ }
                        )

                        FormSection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(3.5f),
                            contentPadding = contentPadding,
                            showCreditCard = true,
                            state = state,
                            onNumberChange = viewModel::onNumberChange,
                            onOwnerChange = viewModel::onOwnerChange,
                            onExpDateChange = viewModel::onExpDateChange,
                            onCVVChange = viewModel::onCVVChange,
                            onAddCardClick = viewModel::onAddCardClick,
                            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
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
    contentPadding: Dp,
    showCreditCard: Boolean,
    state: AddCardScreenState,
    onBackClick: () -> Unit
) {
    Box(
        modifier = modifier.padding(contentPadding),
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_scaffolding),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        IconButton(
            onClick = onBackClick,
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
                    text = stringResource(R.string.add_new_card),
                    style = MaterialTheme.typography.titleSmallSemiBold,
                    color = White
                )
                if (showCreditCard) {
                    Spacer(modifier = Modifier.height(16.dp))
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
}

@Composable
fun FormSection(
    modifier: Modifier,
    contentPadding: Dp,
    showCreditCard: Boolean,
    state: AddCardScreenState,
    onNumberChange: (String) -> Unit,
    onOwnerChange: (String) -> Unit,
    onExpDateChange: (String) -> Unit,
    onCVVChange: (String) -> Unit,
    onAddCardClick: () -> Unit,
    shape: Shape
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground,
        ),
        shape = shape,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (showCreditCard) {
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
            }

            TextFieldWithLabel(
                label = stringResource(R.string.card_number),
                value = state.number,
                onValueChange = onNumberChange,
                maxLength = 16,
                regex = Regex("^\\d{0,16}\$")
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextFieldWithLabel(
                label = stringResource(R.string.cardholder_name),
                value = state.owner,
                onValueChange = onOwnerChange
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(modifier = Modifier.weight(1.5f)) {
                    CompactDateFieldWithLabel(
                        label = stringResource(R.string.exp_date),
                        value = state.exp,
                        onValueChange = onExpDateChange
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    TextFieldWithLabel(
                        label = stringResource(R.string.cvv),
                        value = state.cvv,
                        onValueChange = onCVVChange,
                        maxLength = 3
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onAddCardClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            ) {
                Text(
                    text = stringResource(R.string.add_card),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onBackground
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
fun MediumPhonePortraitPreview() {
    AddCardScreen(mockWindowSizeClass = WindowSizeClass.MediumPhone, onNavigateToDashboard = {})
}

@Preview(
    name = "Medium Phone Landscape",
    device = "spec:width=914dp,height=411dp",
    showBackground = true
)
@Composable
fun MediumPhoneLandscapePreview() {
    AddCardScreen(mockWindowSizeClass = WindowSizeClass.MediumPhoneLandscape, onNavigateToDashboard = {})
}

@Preview(
    name = "Medium Tablet Portrait",
    device = "spec:width=800dp,height=1280dp",
    showBackground = true
)
@Composable
fun MediumTabletPortraitPreview() {
    AddCardScreen(mockWindowSizeClass = WindowSizeClass.MediumTablet,  onNavigateToDashboard = {})
}

@Preview(
    name = "Medium Tablet Landscape",
    device = "spec:width=1280dp,height=800dp",
    showBackground = true
)
@Composable
fun MediumTabletLandscapePreview() {
    AddCardScreen(mockWindowSizeClass = WindowSizeClass.MediumTabletLandscape,  onNavigateToDashboard = {})
}
