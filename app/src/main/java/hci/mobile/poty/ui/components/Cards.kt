package hci.mobile.poty.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hci.mobile.poty.R
import hci.mobile.poty.classes.CreditCard
import hci.mobile.poty.ui.theme.PotyTheme
import hci.mobile.poty.ui.theme.White

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
        CreditCardView(
            creditCard = creditCard,
            isSelected = false,
            onCardClick = {},
            onDeleteCard = {}
        )
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
fun CreditCardView(
    creditCard: CreditCard,
    isSelected: Boolean = false,
    onCardClick: (CreditCard) -> Unit,
    onDeleteCard: (CreditCard) -> Unit
) {

    var dropdownExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .aspectRatio(1.6f)
            .padding(10.dp)
            .shadow(
                elevation = if (isSelected) 16.dp else 8.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onCardClick(creditCard) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
            else
                MaterialTheme.colorScheme.primary,
        ),
        border = if (isSelected) BorderStroke(2.dp, MaterialTheme.colorScheme.onPrimary) else null
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

                Spacer(modifier = Modifier.width(120.dp))

                IconButton(onClick = { dropdownExpanded = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More Options"
                    )
                }
            }



            Spacer(modifier = Modifier.height(50.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ){
                Text(
                    text = creditCard.maskedCardNumber(),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(35.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
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

            DropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Borrar Tarjeta") },
                    onClick = {
                        dropdownExpanded = false
                        onDeleteCard(creditCard)
                    }
                )
            }
        }
    }
}


@Composable
fun FullCreditCardView(creditCard: CreditCard) {
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
                    text = creditCard.number,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(35.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween, 
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

@Composable
fun PaymentCardsCarousel(
    creditCards: List<CreditCard>,
    selectedCard: CreditCard? = null,
    onCardSelected: (CreditCard) -> Unit,
    onAddCardClick: (CreditCard) -> Unit
) {
    val (selectedCardId, setSelectedCardId) = remember { mutableStateOf<String?>(null) }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        items(creditCards) { creditCard ->
            CreditCardView(
                creditCard = creditCard,
                isSelected = selectedCard?.id == creditCard.id,
                onCardClick = { onCardSelected(creditCard) },
                onDeleteCard = {}
            )
        }
        item {
            EmptyCreditCardView(
                onAddCardClick = {
                    val newCard = CreditCard(
                        bank = "New Bank",
                        number = "1122334455667788",
                        owner = "Alice Brown",
                        CVV = "789",
                        exp = "06/26"
                    )
                    onAddCardClick(newCard)
                }
            )
        }
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
        horizontalArrangement = Arrangement.Center
    ) {
        items(creditCards) { creditCard ->
            CreditCardView(
                creditCard = creditCard,
                isSelected = TODO(),
                onCardClick = TODO(),
                onDeleteCard = TODO()
            )
        }
        item {
            EmptyCreditCardView(
                onAddCardClick = {
                    val newCard = CreditCard(
                        bank = "New Bank",
                        number = "1122334455667788",
                        owner = "Alice Brown",
                        CVV = "789",
                        exp = "06/26"
                    )
                    onAddCardClick(newCard)
                }
            )
        }
    }
}

@Preview
@Composable
fun PaymentCardsCarouselPreview() {
    val (selectedCard, setSelectedCard) = remember { mutableStateOf<CreditCard?>(null) }

    val creditCards = remember {
        mutableStateListOf(
            CreditCard(bank = "Banco Royale", number = "1234567812345678", owner = "Le Chiffre", CVV = "123", exp = "12/24"),
            CreditCard(bank = "Banco Quantum", number = "8765432187654321", owner = "Dominic Greene", CVV = "456", exp = "08/25")
        )
    }

    PotyTheme {
        PaymentCardsCarousel(
            creditCards = creditCards,
            selectedCard = null,
            onCardSelected = {setSelectedCard(it) },
            onAddCardClick = { newCard ->
                creditCards.add(newCard)
            }
        )
    }
}