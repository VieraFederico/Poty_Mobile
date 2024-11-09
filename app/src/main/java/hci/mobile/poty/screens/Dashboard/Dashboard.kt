package hci.mobile.poty.screens.Dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hci.mobile.poty.ui.theme.PotyTheme
import hci.mobile.poty.ui.theme.White
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import hci.mobile.poty.ui.theme.titleSmallSemiBold
import hci.mobile.poty.R
import hci.mobile.poty.classes.CreditCard
import hci.mobile.poty.ui.components.BalanceCard
import hci.mobile.poty.ui.components.CardsCarousel
import java.time.LocalTime
import androidx.lifecycle.viewmodel.compose.viewModel


@Preview
@Composable
fun DashboardPreview(){
    Dashboard()
}
@Composable
fun Dashboard(viewModel: DashboardViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()  // Collect state from ViewModel

    PotyTheme(darkTheme = true, dynamicColor = false) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.secondary,
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Top
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Greeting(state.userName)
                    BalanceCard(balance = state.balance, isVisible = state.isBalanceVisible, onToggleVisibility = { viewModel.toggleBalanceVisibility() })
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
                        modifier = Modifier.padding(10.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center

                    ) {
                        DashboardButton(
                            onClick = {},
                            iconResId = R.drawable.corner_right_down,
                            contentDescription = "Ingresar"
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        DashboardButton(
                            onClick = {},
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
                            contentDescription = "Mi CVU"
                        )
                    }
                    CardsCarousel(creditCards = state.creditCards, onAddCardClick = { newCard -> viewModel.addCreditCard(newCard) })
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