package hci.mobile.poty.screens.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hci.mobile.poty.ui.theme.PotyTheme
import hci.mobile.poty.ui.theme.White
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import hci.mobile.poty.ui.theme.titleSmallSemiBold
import hci.mobile.poty.R
import hci.mobile.poty.ui.components.BalanceCard
import java.time.LocalTime
import androidx.lifecycle.viewmodel.compose.viewModel
import hci.mobile.poty.MyApplication
import hci.mobile.poty.screens.register.RegistrationViewModel
import hci.mobile.poty.ui.components.ResponsiveNavBar
import hci.mobile.poty.ui.components.TransactionHistory
import hci.mobile.poty.ui.components.spendingCard
import hci.mobile.poty.utils.WindowSizeClass
import hci.mobile.poty.utils.calculateWindowSizeClass
import hci.mobile.poty.ui.components.PaymentCardsCarousel
import hci.mobile.poty.utils.isTablet


@Composable
fun Dashboard(
    viewModel: DashboardViewModel = viewModel(factory = DashboardViewModel.provideFactory(
        LocalContext.current.applicationContext as MyApplication
    )),
    onNavigateToCharge: () -> Unit,
    onNavigateToDeposit: () -> Unit,
    onNavigateToAddCard: () -> Unit,
    onNavigateToPayment: () -> Unit = {},
    mockWindowSizeClass: WindowSizeClass? = null
) {
    val state by viewModel.state.collectAsState()
    val windowSizeClass = mockWindowSizeClass ?: calculateWindowSizeClass()

    val contentPadding = if (windowSizeClass == WindowSizeClass.MediumTablet || windowSizeClass == WindowSizeClass.MediumTabletLandscape) 32.dp else 16.dp
    val isLandscape = windowSizeClass == WindowSizeClass.MediumPhoneLandscape || windowSizeClass == WindowSizeClass.MediumTabletLandscape

    // Determine if the device is a phone in portrait mode
    val isPhonePortrait = windowSizeClass == WindowSizeClass.MediumPhone


    PotyTheme(darkTheme = true, dynamicColor = false) {
        ResponsiveNavBar(
            onNavigate = { /* Handle navigation */ },
            mockWindowSizeClass = mockWindowSizeClass,
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
                                .weight(if (windowSizeClass == WindowSizeClass.MediumPhoneLandscape) 1f else 0.7f),
                            contentPadding = contentPadding,
                            state = state,
                            onToggleVisibility = { viewModel.toggleBalanceVisibility() },
                            showTransactionHistory = windowSizeClass == WindowSizeClass.MediumPhoneLandscape,
                            windowSizeClass = windowSizeClass
                        )

                        ContentSection(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f),
                            contentPadding = contentPadding,
                            state = state,
                            onNavigateToCharge = onNavigateToCharge,
                            onNavigateToDeposit = onNavigateToDeposit,
                            onNavigateToAddCard = onNavigateToAddCard,
                            onNavigateToPayment = onNavigateToPayment,
                            viewModel = viewModel,
                            windowSizeClass = windowSizeClass,
                            showTransactionHistory = windowSizeClass != WindowSizeClass.MediumPhoneLandscape,
                            topStart = 30.dp,
                            bottomStart =  30.dp

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
                                .fillMaxWidth().fillMaxHeight(if (windowSizeClass == WindowSizeClass.MediumPhone) 0.22f else 0.3f),

                            contentPadding = contentPadding,
                            state = state,
                            onToggleVisibility = { viewModel.toggleBalanceVisibility() },
                            isPhonePortrait = isPhonePortrait,
                            windowSizeClass = windowSizeClass
                        )

                        ContentSection(
                            modifier = Modifier
                                .fillMaxWidth().fillMaxHeight(),
                            contentPadding = contentPadding,
                            state = state,
                            onNavigateToCharge = onNavigateToCharge,
                            onNavigateToDeposit = onNavigateToDeposit,
                            onNavigateToAddCard = onNavigateToAddCard,
                            onNavigateToPayment = onNavigateToPayment,
                            viewModel = viewModel,
                            windowSizeClass = windowSizeClass,
                            topStart = 30.dp,
                            topEnd = 30.dp
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
    state: DashboardState,
    onToggleVisibility: () -> Unit,
    isPhonePortrait: Boolean = false,
    showTransactionHistory: Boolean = false,
    windowSizeClass: WindowSizeClass = WindowSizeClass.MediumPhone
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

        Card(
            modifier = Modifier.fillMaxSize(),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
        ) {
            Column(
                modifier = Modifier
                    .padding(contentPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = if (isPhonePortrait) Arrangement.Top else Arrangement.Center
            ) {
                Greeting(name = state.userName, windowSizeClass = windowSizeClass)
                BalanceCard(
                    balance = state.balance,
                    isVisible = state.isBalanceVisible,
                    onToggleVisibility = onToggleVisibility
                )

                if (showTransactionHistory) {
                    Spacer(modifier = Modifier.height(16.dp))
                    TransactionHistory(transactions = state.transactions, useWhite = true)
                }
            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    windowSizeClass: WindowSizeClass = WindowSizeClass.MediumPhone,
) {
    val currentTime = LocalTime.now().hour

    val greeting = when {
        currentTime in 5..11 -> "¡Buenos Días,"
        currentTime in 12..17 -> "¡Buenas Tardes,"
        else -> "¡Buenas Noches,"
    }

    val textStyle = when (windowSizeClass) {
        WindowSizeClass.MediumTablet, WindowSizeClass.MediumTabletLandscape -> MaterialTheme.typography.titleLarge
        else -> MaterialTheme.typography.titleSmall
    }

    val nameStyle = when (windowSizeClass) {
        WindowSizeClass.MediumTablet, WindowSizeClass.MediumTabletLandscape -> MaterialTheme.typography.titleLarge
        else -> MaterialTheme.typography.titleSmallSemiBold
    }

    val hasEnter =  when (windowSizeClass) {
        WindowSizeClass.MediumTabletLandscape -> true
        else -> false
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        if (hasEnter) {
            Text(
                text = greeting,
                style = textStyle,
                color = White
            )
            Text(
                text = "$name!",
                style = nameStyle,
                color = White
            )
        } else {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = White, fontSize = textStyle.fontSize, fontWeight = textStyle.fontWeight)) {
                        append(greeting)
                        append(" ")
                    }
                    withStyle(style = SpanStyle(color = White, fontSize = nameStyle.fontSize, fontWeight = nameStyle.fontWeight)) {
                        append(name)
                        append("!")
                    }
                }
            )
        }
    }
}


@Composable
fun ContentSection(
    modifier: Modifier,
    contentPadding: Dp,
    state: DashboardState,
    onNavigateToCharge: () -> Unit,
    onNavigateToDeposit: () -> Unit,
    onNavigateToAddCard: () -> Unit,
    onNavigateToPayment: () -> Unit,
    viewModel: DashboardViewModel,
    windowSizeClass: WindowSizeClass,
    showTransactionHistory: Boolean = true,
    topStart: Dp = 0.dp,
    topEnd: Dp = 0.dp,
    bottomStart: Dp = 0.dp,
    bottomEnd: Dp = 0.dp
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground,
        ),
        shape = RoundedCornerShape(
            topStart = topStart,
            topEnd = topEnd,
            bottomStart = bottomStart,
            bottomEnd = bottomEnd
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                DashboardButton(
                    onClick = onNavigateToDeposit,
                    iconResId = R.drawable.corner_right_down,
                    contentDescription = "Ingresar"
                )
                Spacer(modifier = Modifier.width(20.dp))
                DashboardButton(
                    onClick = onNavigateToCharge,
                    iconResId = R.drawable.dollar_sign,
                    contentDescription = "Cobrar"
                )
                Spacer(modifier = Modifier.width(20.dp))
                DashboardButton(
                    onClick =  onNavigateToPayment,
                    iconResId = R.drawable.corner_right_up,
                    contentDescription = "Enviar"
                )
                Spacer(modifier = Modifier.width(20.dp))
                DashboardButton(
                    onClick = { /* TODO */ },
                    iconResId = R.drawable.user,
                    contentDescription = "CVU"
                )
            }
            if(windowSizeClass.isTablet()){
                spendingCard(spent = state.spent)
            }
            PaymentCardsCarousel(
                creditCards = state.creditCards,
                selectedCard = null,
                onCardSelected = {},
                onNavigateToAddCard = onNavigateToAddCard,
                onDeleteCard = { cardId -> viewModel.deleteCreditCard(cardId) },
                windowSizeClass = windowSizeClass
            )
            if (showTransactionHistory) {
                TransactionHistory(transactions = state.transactions)
            }
        }
    }
}



@Composable
fun DashboardButton(
    onClick: () -> Unit,
    iconResId: Int,
    contentDescription: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ){
        FloatingActionButton(
            onClick = onClick,
            containerColor = MaterialTheme.colorScheme.primary,
            shape = CircleShape,
            modifier = Modifier.padding(10.dp)
        ) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = contentDescription,
                tint = White,
                modifier = Modifier.size(35.dp)
            )
        }
        Text(
            text = contentDescription,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

