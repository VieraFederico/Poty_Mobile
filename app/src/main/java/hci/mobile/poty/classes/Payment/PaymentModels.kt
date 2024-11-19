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

data class PaymentResponse(
    val id: Int,
    val amount: Double,
    val description: String,
    val type: String,
    val createdAt: String,
    val updatedAt: String,
    val status: String
)

data class SettleRequest(val type: String)
data class SettleResponse(val success: Boolean)
