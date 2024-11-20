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
import hci.mobile.poty.ui.theme.titleSmallSemiBold
import hci.mobile.poty.R
import hci.mobile.poty.ui.components.BalanceCard
import hci.mobile.poty.ui.components.CardsCarousel
import java.time.LocalTime
import androidx.lifecycle.viewmodel.compose.viewModel
import hci.mobile.poty.ui.components.BottomNavBar
import hci.mobile.poty.ui.components.PaymentCardsCarousel
import hci.mobile.poty.ui.components.TransactionHistory
import hci.mobile.poty.ui.components.spendingCard


@Preview
@Composable
fun DashboardPreview(){
    Dashboard(
        onNavigateToCharge = {},
        onNavigateToDeposit = {},
        onNavigateToAddCard = {}
    )
}
@Composable
fun Dashboard(viewModel: DashboardViewModel = viewModel(),
              onNavigateToCharge: () -> Unit, // Callback para ir a ChargeScreen
              onNavigateToDeposit: () -> Unit, // Callback para ir a DepositScreen
              onNavigateToAddCard: () -> Unit
     ) {
    val state by viewModel.state.collectAsState()  // Collect state from ViewModel

    PotyTheme(darkTheme = true, dynamicColor = false) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.secondary,
            bottomBar = { BottomNavBar { } },
        ) { innerPadding ->
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
                ) {
                    // Background image
                    Image(
                        painter = painterResource(id = R.drawable.background_scaffolding),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // Top Card with content on top of the image
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent // Set to transparent to let image show through
                        ),
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Greeting(state.userName)
                            BalanceCard(
                                balance = state.balance,
                                isVisible = state.isBalanceVisible,
                                onToggleVisibility = { viewModel.toggleBalanceVisibility() }
                            )
                        }
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2.5f),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.onBackground,
                    ),
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp, bottomEnd = 0.dp, bottomStart = 0.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Row (
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center

                    ) {
                        DashboardButton(
                            onClick = {onNavigateToDeposit()},
                            iconResId = R.drawable.corner_right_down,
                            contentDescription = "Ingresar"
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        DashboardButton(
                            onClick = {onNavigateToCharge()},
                            iconResId = R.drawable.dollar_sign,
                            contentDescription = "Cobrar"
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        DashboardButton(
                            onClick = {},
                            iconResId = R.drawable.corner_right_up,
                            contentDescription = "Enviar"
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        DashboardButton(
                            onClick = {},
                            iconResId = R.drawable.user,
                            contentDescription = "CVU"
                        )
                    }
                    spendingCard(spent = state.spent)
                    PaymentCardsCarousel(
                        creditCards = state.creditCards,
                        selectedCard = null,
                        onCardSelected = { },
                        onNavigateToAddCard = { /* Para Sangui :) */ },
                        onDeleteCard = { cardId -> viewModel.deleteCreditCard(cardId) }
                    )

                    TransactionHistory(transactions = state.transactions)
                }
            }
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
    iconResId: Int,  // Add parameter for the icon resource ID
    contentDescription: String // Add parameter for the content description
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ){
        FloatingActionButton(
            onClick = { onClick() },
            containerColor = MaterialTheme.colorScheme.primary,
            shape = CircleShape,
            modifier = Modifier.padding(10.dp)
        ) {
            Icon(
                painter = painterResource(id = iconResId),  // Use the passed resource ID
                contentDescription = contentDescription,  // Use the passed content description
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