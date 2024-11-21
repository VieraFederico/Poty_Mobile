package hci.mobile.poty.screens.payment

import CardResponse
import androidx.compose.runtime.mutableStateListOf

data class PaymentScreenState(
    val currentStep: Int = 1,
    val balance: Double = 567.0, //Llamada API?
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
    val email: String = "",
    val paymentLink: String = "",
    val type: PaymentType = PaymentType.CARD,
    val request: PaymentRequest,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
)

sealed class PaymentRequest(
    open val amount: Double,
    open val description: String,
    open val type: String
) {
    data class BalancePayment(
        override val amount: Double,
        override val description: String,
        override val type: String = "BALANCE",
        val receiverEmail: String
    ) : PaymentRequest(amount, description, type)

    data class CardPayment(
        override val amount: Double,
        override val description: String,
        override val type: String = "CARD",
        val cardId: Int,
        val receiverEmail: String
    ) : PaymentRequest(amount, description, type)
}

enum class PaymentType {
    BALANCE, CARD
}