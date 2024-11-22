package hci.mobile.poty.classes

data class BalanceResponse(val balance: Float)
data class RechargeRequest(val amount: Float)

data class InvestmentResponse(val investment: Double)
data class InvestRequest(val amount: Float)
data class DivestRequest(val amount: Float)

data class CardResponse(
    val id: Int,
    val number: String,
    val expirationDate: String,
    val fullName: String,
    val type: String
)

data class AddCardRequest(
    val fullName: String,
    val cvv: String,
    val number: String,
    val expirationDate: String,
    val type: String
)

data class DeleteResponse(val status: String)

data class DailyReturnResponse(
    val id: Int,
    val returnGiven: Float,
    val balanceBefore: Float,
    val balanceAfter: Float,
    val createdAt: String
)

data class InterestResponse(val interest: Float)

data class AliasUpdateRequest(val alias: String)
data class WalletDetailsResponse(
    val id: Int,
    val balance: Float,
    val invested: Float,
    val cbu: String,
    val alias: String
)

data class PaymentResponse(
    val id: Int,
    val type: String,
    val amount: Float,
    val balanceBefore: Float,
    val balanceAfter: Float,
    val pending: Boolean,
    val linkUuid: String?,
    val createdAt: String,
    val updatedAt: String,
    val card: CardDetails? // Card details, if available
)

data class CardDetails(
    val id: Int,
    val number: String,
    val expirationDate: String,
    val fullName: String,
    val type: String
)