package hci.mobile.poty.ui.components

import android.annotation.SuppressLint
import hci.mobile.poty.data.model.Card
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
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.Color

import hci.mobile.poty.data.model.CardType

import androidx.compose.ui.res.stringResource
import hci.mobile.poty.ui.theme.Black

import hci.mobile.poty.utils.WindowSizeClass

@Preview
@Composable
fun CreditCardViewPreview(){
    val creditCard = Card(
        id = 1,
        number = "1234567812345678",
        type = CardType.DEBIT,
        fullName = "Jason Bourne",
        expirationDate = "09/26",
        cvv = "123",

    )
    PotyTheme {
        CreditCardView(
            card = creditCard,
            isSelected = false,
            onCardClick = {},
            onDeleteCard = {},

        )
    }
}




@Composable
fun EmptyCreditCardView(onAddCardClick: () -> Unit, isTiny: Boolean = false, useWhite: Boolean = false) {
    val containerColor = if (useWhite) Color.White else MaterialTheme.colorScheme.primary
    val contentColor = if (useWhite)  Color.Black else MaterialTheme.colorScheme.onSurface

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
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(80.dp)
                )
            }
            if (!isTiny) {
                Text(
                    text = stringResource(R.string.add_card),
                    modifier = Modifier.padding(top = 10.dp),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    }
}


@SuppressLint("SuspiciousIndentation")
@Composable
fun CreditCardView(
    card: Card,
    isSelected: Boolean = false,
    onCardClick: (Card) -> Unit,
    onDeleteCard: (Int) -> Unit,
    isTiny: Boolean = false,
    useWhite: Boolean = false
) {
    var dropdownExpanded by remember { mutableStateOf(false) }

    val contentColor = if (useWhite) Black else MaterialTheme.colorScheme.onSurface

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
            .clickable { onCardClick(card) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (useWhite) Color.White else MaterialTheme.colorScheme.primary
        ),
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
            ) {
                if(!useWhite){
                    Image(
                        painter = painterResource(id = R.drawable.poty),
                        contentDescription = "Poty Logo",
                        modifier = Modifier.size(35.dp)
                    )

                }

                Spacer(
                    modifier = Modifier.width(
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
                        tint = contentColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(35.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = card.number,
                    style = MaterialTheme.typography.labelLarge,
                    color = contentColor
                )
            }

            Spacer(modifier = Modifier.height(30.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = card.fullName,
                    style = MaterialTheme.typography.bodyLarge,
                    color = contentColor
                )

                Text(
                    text = card.expirationDate,
                    style = MaterialTheme.typography.bodyLarge,
                    color = contentColor
                )
            }

            DropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false }
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.delete_card),
                            color = contentColor
                        )
                    },
                    onClick = {
                        dropdownExpanded = false
                        card.id?.let { onDeleteCard(it) }
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
    creditCards: List<Card>,
    selectedCard: Card? = null,
    onCardSelected: (Card) -> Unit,
    onNavigateToAddCard: () -> Unit,
    onDeleteCard: (Int) -> Unit,
    windowSizeClass: WindowSizeClass = WindowSizeClass.MediumPhone,
    modifier: Modifier = Modifier,
    useWhite: Boolean = false,
    isTiny: Boolean = false,
) {
    val allItems = creditCards + null

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
            contentPadding = contentPadding
        ) { page ->
            val item = allItems[page]
            if (item != null) {
                CreditCardView(
                    card = item,
                    isSelected = selectedCard?.id == item.id,
                    onCardClick = { onCardSelected(item) },
                    onDeleteCard = { item.id?.let { it1 -> onDeleteCard(it1) } },
                    isTiny = isTiny,
                    useWhite = useWhite
                )
            } else {
                EmptyCreditCardView(
                    onAddCardClick = { onNavigateToAddCard() },
                    isTiny = isTiny,
                    useWhite = useWhite
                )
            }
        }
    }
}


@Composable
fun CardsCarousel(
    creditCards: List<Card>,
    selectedCard: Card? = null,
    onCardSelected: (Card) -> Unit,
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
                card = creditCard,
                isSelected = false,
                onCardClick = {},
                onDeleteCard = {}
            )
        }
        item {
            EmptyCreditCardView(
                onAddCardClick = {onNavigateToAddCard()}
            )
        }
    }
}





