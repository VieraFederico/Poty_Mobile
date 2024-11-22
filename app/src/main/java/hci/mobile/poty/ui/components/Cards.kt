package hci.mobile.poty.ui.components

import hci.mobile.poty.classes.CardResponse
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
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import hci.mobile.poty.ui.theme.Black
import hci.mobile.poty.utils.WindowSizeClass

@Preview
@Composable
fun CreditCardViewPreview(){
    val creditCard = CardResponse(
        id = 7,
        number = "1234567812345678",
        type = "Credito",
        fullName = "James Bond",
        expirationDate = "03/60",

    )
    PotyTheme {
        CreditCardView(
            Card = creditCard,
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
    Card: CardResponse,
    isSelected: Boolean = false,
    onCardClick: (CardResponse) -> Unit,
    onDeleteCard: (Int) -> Unit,
    isTiny: Boolean = false
) {

    var dropdownExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .aspectRatio(1.6f)
            .padding(
                when (isTiny) {
                    true -> 5.dp
                    else -> 10.dp
                }
            )
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .fillMaxWidth()
            .clickable { onCardClick(Card) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
    ) {
        Column(
            modifier = Modifier
                .padding(
                    when (isTiny) {
                        true -> 5.dp
                        else -> 10.dp
                    }
                )
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
                    text = Card.type,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.width(
                    when (isTiny) {
                        true -> 110.dp
                        else -> 180.dp
                    }
                )
                )

                IconButton(onClick = { dropdownExpanded = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More Options",
                        tint = White
                    )
                }
            }



            Spacer(modifier = Modifier.height(35.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ){
                Text(
                    text = Card.number,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ){
                Text(
                    text = Card.fullName,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = Card.expirationDate,
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
                        onDeleteCard(Card.id)
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PaymentCardsCarousel(
    creditCards: List<CardResponse>,
    selectedCard: CardResponse? = null,
    onCardSelected: (CardResponse) -> Unit,
    onNavigateToAddCard: () -> Unit,
    onDeleteCard: (Int) -> Unit,
    windowSizeClass: WindowSizeClass = WindowSizeClass.MediumPhone,
    modifier: Modifier = Modifier,
    isTiny: Boolean = false
) {
    val allItems = creditCards + null

    // Calculate dynamic padding based on WindowSizeClass
    val contentPadding = when (windowSizeClass) {
        WindowSizeClass.MediumTablet  -> PaddingValues(start = 150.dp, end = 200.dp)
        WindowSizeClass.MediumTabletLandscape -> PaddingValues(start = 0.dp, end = 120.dp)
        WindowSizeClass.MediumPhoneLandscape -> PaddingValues(start = 0.dp, end = 120.dp)
        else -> PaddingValues(start = 0.dp, end = 70.dp)
    }

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { allItems.size }
    )

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            val selectedItem = allItems.getOrNull(page)
            if (selectedItem != null) {
                onCardSelected(selectedItem)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp),
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = modifier
                .fillMaxWidth()
                .height(220.dp),
            contentPadding = contentPadding // Apply dynamic padding here
        ) { page ->
            val item = allItems[page]
            if (item != null) {
                CreditCardView(
                    Card = item,
                    isSelected = selectedCard?.id == item.id,
                    onCardClick = { onCardSelected(item) },
                    onDeleteCard = {},
                    isTiny = isTiny
                )
            } else {
                EmptyCreditCardView(
                    onAddCardClick = { onNavigateToAddCard() }
                )
            }
        }
    }
}


@Composable
fun CardsCarousel(
    creditCards: List<CardResponse>,
    selectedCard: CardResponse? = null,
    onCardSelected: (CardResponse) -> Unit,
    onNavigateToAddCard: () -> Unit,
    onDeleteCard: (Int) -> Unit
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
                Card = creditCard,
                isSelected = false,
                onCardClick = {},
                onDeleteCard = {}
            )
        }
        item {
            EmptyCreditCardView(
                onAddCardClick = {onNavigateToAddCard}
            )
        }
    }
}





@Preview
@Composable
fun CardsCarouselPreview() {
    val creditCards = listOf(
        CardResponse(
            id = 1,
            number = "1234567812345678",
            type = "Debito",
            fullName = "Jason Bourne",
            expirationDate = "09/26"
        ),
        CardResponse(
            id = 2,
            number = "8765432187654321",
            type = "Credito",
            fullName = "Ethan Hunt",
            expirationDate = "07/27"
        )
    )
    PotyTheme {
        PaymentCardsCarousel(
            creditCards = creditCards,
            selectedCard = creditCards.firstOrNull(),
            onCardSelected = {},
            onNavigateToAddCard = {},
            onDeleteCard = {}
        )
    }
}

