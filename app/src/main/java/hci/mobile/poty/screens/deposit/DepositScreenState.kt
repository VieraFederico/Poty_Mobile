package hci.mobile.poty.screens.deposit

import hci.mobile.poty.data.model.Card
import androidx.compose.runtime.mutableStateListOf
import hci.mobile.poty.classes.CreditCard

data class DepositScreenState(
    val number: Float = 0f,
    val errorMessage: String? = null,
    val selectedCard: Card? = null,
    val creditCards: List<Card> = emptyList(),
)
