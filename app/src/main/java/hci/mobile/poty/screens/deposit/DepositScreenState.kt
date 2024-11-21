package hci.mobile.poty.screens.deposit

import hci.mobile.poty.classes.CardResponse
import androidx.compose.runtime.mutableStateListOf
import hci.mobile.poty.classes.CreditCard

data class DepositScreenState(
    val number: Float = 0f,
    val errorMessage: String? = null,
    val selectedCard: CardResponse? = null,
    val creditCards: List<CardResponse> = mutableStateListOf(
        CardResponse(
            id = 1,
            number = "1234567812345678",
            type = "Crédito",
            fullName = "James Bond",
            expirationDate = "12/24"
        ),
        CardResponse(
            id = 2,
            number = "8765432187654321",
            type = "Débito",
            fullName = "James Bond",
            expirationDate = "08/25"
        )
    ),
)
