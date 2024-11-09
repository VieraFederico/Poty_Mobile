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
import java.time.LocalTime


@Preview
@Composable
fun DashboardPreview(){
    Dashboard()
}
@Composable
fun Dashboard(){
    val creditCards = remember { mutableStateListOf<CreditCard>() }
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
            ){
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)

                ){
                    Greeting("James Bond")
                    BalanceCard(10F)
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2.5f),  // 3/4 of the screen height
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.onBackground,
                    ),
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp, bottomEnd = 0.dp, bottomStart = 0.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    )
                ){
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
                    CardsCarousel(
                        creditCards = creditCards,
                        onAddCardClick = { newCard ->
                            creditCards.add(newCard) // Add a new card to the list
                        }
                    )
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
fun BalanceCard(balance: Float){
    var isVisible by remember { mutableStateOf(true) }
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
    ) {
        Text(
            text = "Tu Balance",
            color = White,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 3.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            if(isVisible) {
                Text(
                    text = "$ $balance",
                    color = White,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            else{
                Text(
                    text = "$ ****",
                    color = White,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            IconButton(
                onClick = { isVisible = !isVisible },
                modifier = Modifier.padding(start = 10.dp)
            ){
                Icon(
                    painter = painterResource(id = if (isVisible) R.drawable.eye else R.drawable.eye_off),
                    contentDescription = if (isVisible) "Ocular Balance" else "Mostrar Balance",
                    tint = White,
                    modifier = Modifier.size(35.dp)
                )
            }
        }
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

@Preview
@Composable
fun CreditCardViewPreview(){
    val creditCard = CreditCard(
        bank = "Banco Royale",
        number = "1234567812345678",
        owner = "James Bond",
        CVV = "007",
        exp = "03/60"
    )
    PotyTheme {
        CreditCardView(creditCard)
    }
}

@Preview
@Composable
fun EmptyCreditCardViewPreview(){
    PotyTheme {
        EmptyCreditCardView(onAddCardClick = { /* Define your click action here */ })
    }
}
@Composable
fun EmptyCreditCardView(onAddCardClick: () -> Unit) {
    Card(
        modifier = Modifier
            .aspectRatio(1.6f)
            .padding(16.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = onAddCardClick,
                modifier = Modifier.size(56.dp),
            ) {
                Icon(
                    imageVector = Icons.Outlined.AddCircle,
                    contentDescription = "Add a new card",
                    tint = White,
                    modifier = Modifier.size(80.dp)
                )
            }
            Text(
                text = "Agregar una nueva tarjeta",
                modifier = Modifier.padding(top=10.dp),
                style = MaterialTheme.typography.labelLarge,
                color = White,
            )
        }
    }
}
@Composable
fun CreditCardView(creditCard: CreditCard) {
    Card(
        modifier = Modifier
            .aspectRatio(1.6f)
            .padding(10.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
            ),
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ){
                Image(
                    painter = painterResource(id = R.drawable.poty),
                    contentDescription = "Poty Logo",
                    modifier = Modifier.size(35.dp)

                )
                Text(
                    text = creditCard.bank,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(50.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ){
                Text(
                    text = "${creditCard.maskedCardNumber()}",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(35.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween, // Align text to opposite ends
            ){
                Text(
                    text = creditCard.owner,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )


                Text(
                    text = creditCard.exp,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}


@Preview
@Composable
fun CardsCarouselPreview() {
    // Create a list of sample credit cards for preview
    val creditCards = remember {
        mutableStateListOf(
            CreditCard(bank = "Banco Royale", number = "1234567812345678", owner = "Le Chiffre", CVV = "123", exp = "12/24"),
            CreditCard(bank = "Banco Quantum", number = "8765432187654321", owner = "Dominic Greene", CVV = "456", exp = "08/25")
        )
    }

    PotyTheme {
        CardsCarousel(
            creditCards = creditCards,
            onAddCardClick = { newCard ->
                creditCards.add(newCard) // Add a new card to the list
            }
        )
    }
}

@Composable
fun CardsCarousel(
    creditCards: List<CreditCard>,
    onAddCardClick: (CreditCard) -> Unit
) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,

            ) {
            items(creditCards) { creditCard ->
                CreditCardView(creditCard = creditCard)
            }
        item {
            EmptyCreditCardView(
                onAddCardClick = {
                    onAddCardClick(
                        CreditCard(
                            bank = "New Bank",
                            number = "1122334455667788",
                            owner = "Alice Brown",
                            CVV = "789",
                            exp = "06/26"
                        )
                    )
                }
            )
        }
    }
}



