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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import hci.mobile.poty.ui.theme.titleSmallSemiBold
import hci.mobile.poty.R
import hci.mobile.poty.ui.components.BalanceCard
import java.time.LocalTime
import androidx.lifecycle.viewmodel.compose.viewModel
import hci.mobile.poty.ui.components.ResponsiveNavBar
import hci.mobile.poty.ui.components.TransactionHistory
import hci.mobile.poty.ui.components.spendingCard
import hci.mobile.poty.utils.WindowSizeClass
import hci.mobile.poty.utils.calculateWindowSizeClass
import hci.mobile.poty.ui.components.PaymentCardsCarousel


@Composable
fun Dashboard(
    viewModel: DashboardViewModel = viewModel(),
    onNavigateToCharge: () -> Unit,
    onNavigateToDeposit: () -> Unit,
    onNavigateToAddCard: () -> Unit,
    mockWindowSizeClass: WindowSizeClass? = null
) {
    val state by viewModel.state.collectAsState()
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
                            state = state,
                            onToggleVisibility = { viewModel.toggleBalanceVisibility() }
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
                            viewModel = viewModel,
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
                                .weight(1f),
                            contentPadding = contentPadding,
                            state = state,
                            onToggleVisibility = { viewModel.toggleBalanceVisibility() }
                        )

                        ContentSection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(2.5f),
                            contentPadding = contentPadding,
                            state = state,
                            onNavigateToCharge = onNavigateToCharge,
                            onNavigateToDeposit = onNavigateToDeposit,
                            onNavigateToAddCard = onNavigateToAddCard,
                            viewModel = viewModel,
                            windowSizeClass = windowSizeClass
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
    onToggleVisibility: () -> Unit
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
                verticalArrangement = Arrangement.Center
            ) {
                Greeting(state.userName)
                BalanceCard(
                    balance = state.balance,
                    isVisible = state.isBalanceVisible,
                    onToggleVisibility = onToggleVisibility
                )
            }
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
    viewModel: DashboardViewModel,
    windowSizeClass: WindowSizeClass
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground,
        ),
        shape = RoundedCornerShape(
            topStart = if (modifier.fillMaxWidth() == Modifier.fillMaxWidth()) 30.dp else 0.dp,
            topEnd = 30.dp,
            bottomStart = if (modifier.fillMaxWidth() == Modifier.fillMaxWidth()) 0.dp else 30.dp,
            bottomEnd = 30.dp
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(contentPadding),
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
                    onClick = { /* TODO */ },
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
            spendingCard(spent = state.spent)
            PaymentCardsCarousel(
                creditCards = state.creditCards,
                selectedCard = null,
                onCardSelected = { },
                onNavigateToAddCard = onNavigateToAddCard,
                onDeleteCard = { cardId -> viewModel.deleteCreditCard(cardId) },
                windowSizeClass =  windowSizeClass
            )
            TransactionHistory(transactions = state.transactions)
        }
    }
}

@Composable
fun Greeting(name: String){
    val currentTime = LocalTime.now().hour

    val greeting = when {
        currentTime in 5..11 -> "¡Buenos Días,"
        currentTime in 12..17 -> "¡Buenas Tardes,"
        else -> "¡Buenas Noches,"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .padding(bottom = 20.dp)
    ){
        Text(
            text = greeting,
            style = MaterialTheme.typography.titleSmall,
            color = White
        )
        Text(
            text = "$name!",
            style = MaterialTheme.typography.titleSmallSemiBold,
            color = White
        )
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


@Preview(
    name = "Medium Phone Portrait",
    device = "spec:width=411dp,height=914dp",
    showBackground = true
)
@Composable
fun MediumPhonePortraitPreview() {
    Dashboard(
        onNavigateToCharge = {},
        onNavigateToDeposit = {},
        onNavigateToAddCard = {},
        mockWindowSizeClass = WindowSizeClass.MediumPhone
    )
}

@Preview(
    name = "Medium Phone Landscape",
    device = "spec:width=914dp,height=411dp",
    showBackground = true
)
@Composable
fun MediumPhoneLandscapePreview() {
    Dashboard(
        onNavigateToCharge = {},
        onNavigateToDeposit = {},
        onNavigateToAddCard = {},
        mockWindowSizeClass = WindowSizeClass.MediumPhoneLandscape
    )
}

@Preview(
    name = "Medium Tablet Portrait",
    device = "spec:width=800dp,height=1280dp",
    showBackground = true
)
@Composable
fun MediumTabletPortraitPreview() {
    Dashboard(
        onNavigateToCharge = {},
        onNavigateToDeposit = {},
        onNavigateToAddCard = {},
        mockWindowSizeClass = WindowSizeClass.MediumTablet
    )
}

@Preview(
    name = "Medium Tablet Landscape",
    device = "spec:width=1280dp,height=800dp",
    showBackground = true
)
@Composable
fun MediumTabletLandscapePreview() {
    Dashboard(
        onNavigateToCharge = {},
        onNavigateToDeposit = {},
        onNavigateToAddCard = {},
        mockWindowSizeClass = WindowSizeClass.MediumTabletLandscape
    )
}
