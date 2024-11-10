package hci.mobile.poty.screens.Deposit

import androidx.compose.runtime.mutableStateListOf
import hci.mobile.poty.classes.CreditCard

data class DepositScreenState(
    val number: Float = 0f,
    val errorMessage: String? = null,
    val selectedCard: CreditCard? = null,
    val creditCards: List<CreditCard> = mutableStateListOf(
        CreditCard(
            bank = "Banco Royale",
            number = "1234567812345678",
            owner = "Le Chiffre",
            CVV = "123",
            exp = "12/24"
        ),
        CreditCard(
            bank = "Banco Quantum",
            number = "8765432187654321",
            owner = "Dominic Greene",
            CVV = "456",
            exp = "08/25"
        )
    ),
)