package hci.mobile.poty.screens.deposit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hci.mobile.poty.MyApplication
import hci.mobile.poty.R
import hci.mobile.poty.ui.components.BackButton
import hci.mobile.poty.ui.components.PaymentCardsCarousel
import hci.mobile.poty.ui.theme.White
import hci.mobile.poty.ui.theme.titleSmallSemiBold
import hci.mobile.poty.utils.NumberFieldWithLabel
import hci.mobile.poty.utils.WindowSizeClass
import hci.mobile.poty.utils.calculateWindowSizeClass
import hci.mobile.poty.ui.components.ResponsiveNavBar
import hci.mobile.poty.utils.isLandscape
import hci.mobile.poty.utils.isTablet

@Composable
fun DepositScreen(
    viewModel: DepositScreenViewModel = viewModel(factory = DepositScreenViewModel.provideFactory(
        LocalContext.current.applicationContext as MyApplication, LocalContext.current
    )),
    onNavigateToAddCard: () -> Unit = {},
    onNavigateToDashboard: () -> Unit = {},

) {
    val state by viewModel.state.collectAsState()
    val windowSizeClass =  calculateWindowSizeClass()

    val isLandscape = windowSizeClass == WindowSizeClass.MediumPhoneLandscape || windowSizeClass == WindowSizeClass.MediumTabletLandscape
    val contentPadding = if (windowSizeClass == WindowSizeClass.MediumTablet || windowSizeClass == WindowSizeClass.MediumTabletLandscape) 32.dp else 16.dp

    viewModel.onDepositSuccess = onNavigateToDashboard

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
                        DepositHeaderSection(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f),
                            onNavigateToDashboard = onNavigateToDashboard,
                            contentPadding = contentPadding,
                            windowSizeClass = windowSizeClass,
                            state = state,
                            viewModel = viewModel,
                            onNavigateToAddCard = onNavigateToAddCard
                        )

                        DepositContentSection(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f),
                            state = state,
                            viewModel = viewModel,
                            onNavigateToAddCard = onNavigateToAddCard,
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

                        DepositHeaderSection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(headerWeight),
                            onNavigateToDashboard = onNavigateToDashboard,
                            contentPadding = contentPadding,
                            windowSizeClass = windowSizeClass,
                            state = state,
                            viewModel = viewModel,
                            onNavigateToAddCard = onNavigateToAddCard
                        )

                        DepositContentSection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(3.5f),
                            state = state,
                            viewModel = viewModel,
                            onNavigateToAddCard = onNavigateToAddCard,
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
fun DepositHeaderSection(
    modifier: Modifier,
    onNavigateToDashboard: () -> Unit,
    contentPadding: Dp,
    windowSizeClass: WindowSizeClass,
    state: DepositScreenState,
    viewModel: DepositScreenViewModel,
    onNavigateToAddCard: () -> Unit
) {
    val isPhoneLandscape = windowSizeClass == WindowSizeClass.MediumPhoneLandscape

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

        BackButton()
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
                    text = stringResource(R.string.deposit_money),
                    style = if (windowSizeClass == WindowSizeClass.MediumTablet || windowSizeClass == WindowSizeClass.MediumTabletLandscape) {
                        MaterialTheme.typography.titleLarge
                    } else {
                        MaterialTheme.typography.titleSmallSemiBold
                    },
                    color = White
                )

                if (isPhoneLandscape) {
                    Spacer(modifier = Modifier.height(16.dp))
                    PaymentCardsCarousel(
                        creditCards = state.creditCards,
                        selectedCard = state.selectedCard,
                        onCardSelected = { card -> viewModel.onCardSelect(card) },
                        onNavigateToAddCard = { onNavigateToAddCard() },
                        onDeleteCard = { cardId -> viewModel.deleteCreditCard(cardId) },
                        windowSizeClass = windowSizeClass,
                        useWhite = true
                    )
                }
            }
        }
    }
}



@Composable
fun DepositContentSection(
    modifier: Modifier,
    state: DepositScreenState,
    viewModel: DepositScreenViewModel,
    onNavigateToAddCard: () -> Unit,
    contentPadding: Dp,
    windowSizeClass: WindowSizeClass
) {
    val isPhoneLandscape = windowSizeClass == WindowSizeClass.MediumPhoneLandscape

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
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!isPhoneLandscape) {
                Spacer(modifier = Modifier.height(if (windowSizeClass.isTablet()) 60.dp else 30.dp))

                PaymentCardsCarousel(
                    creditCards = state.creditCards,
                    selectedCard = state.selectedCard,
                    onCardSelected = { card -> viewModel.onCardSelect(card) },
                    onNavigateToAddCard = { onNavigateToAddCard() },
                    onDeleteCard = { cardId -> viewModel.deleteCreditCard(cardId) },
                    windowSizeClass = windowSizeClass
                )

                Spacer(modifier = Modifier.height(if (windowSizeClass.isTablet()) 50.dp else 25.dp))
            } else {
                Spacer(modifier = Modifier.height(16.dp))
            }

            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NumberFieldWithLabel(
                    label = stringResource(R.string.amount_to_deposit),
                    value = state.number,
                    onValueChange = { newValue ->
                        viewModel.onNumberChange(newValue)
                    }
                )
                state.errorMessage?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(25.dp))

                Button(
                    onClick = { viewModel.onDepositClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                ) {
                    Text(
                        text = stringResource(R.string.deposit),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}


