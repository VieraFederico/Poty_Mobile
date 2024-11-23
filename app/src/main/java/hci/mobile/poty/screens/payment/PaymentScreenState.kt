package hci.mobile.poty.screens.payment

import androidx.compose.runtime.mutableStateListOf
import hci.mobile.poty.classes.CardDetails
import hci.mobile.poty.data.model.Card
import hci.mobile.poty.classes.PaymentResponse
import hci.mobile.poty.data.model.LinkPaymentType

data class PaymentScreenState(
    var amount: Float = 0f,
    val currentStep: Int = 1,
    val balance: Float = 567.0f,
    val selectedCard: Card? = null,
    val creditCards: List<Card> = emptyList(),
    val email: String = "",
    val description: String,
    var paymentLink: String = "",
    val type: LinkPaymentType = LinkPaymentType.CARD,
    val request: PaymentRequest,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val paymentHistory: List<PaymentResponse> = mutableStateListOf(
        PaymentResponse(
        id = 1,
        type = "CARD",
        amount = 150.0f,
        balanceBefore = 0.0f,
        balanceAfter = 0.0f,
        pending = true,
        linkUuid = null,
        createdAt = "2023-12-23",
        updatedAt = "2023-12-23",
        card = CardDetails(
            id = 1,
            number = "1234567890123452",
            expirationDate = "04/28",
            fullName = "John Doe",
            type = "CREDIT"
            )
        ),
        PaymentResponse(
            id = 1,
            type = "BALANCE",
            amount = 4523.0f,
            balanceBefore = 0.0f,
            balanceAfter = 0.0f,
            pending = true,
            linkUuid = null,
            createdAt = "2023-12-23",
            updatedAt = "2023-12-23",
            card = CardDetails(
                id = 1,
                number = "1234567890123452",
                expirationDate = "04/28",
                fullName = "John Doe",
                type = "CREDIT"
            )
        )
    )
)

sealed class PaymentRequest(
    open var amount: Float,
    open val description: String,
    open val type: String
) {
    data class BalancePayment(
        override var amount: Float,
        override val description: String,
        override val type: String = "BALANCE",
        val receiverEmail: String
    ) : PaymentRequest(amount, description, type)

    data class CardPayment(
        override var amount: Float,
        override val description: String,
        override val type: String = "CARD",
        val cardId: Int,
        val receiverEmail: String
    ) : PaymentRequest(amount, description, type)

    data class linkPayment(
        override var amount: Float,
        override val description: String,
        override val type: String = "LINK",
        val linkUuid: String
    ) : PaymentRequest(amount, description, type)
}

enum class PaymentType {
    BALANCE, CARD
}