data class BalanceResponse(val balance: Double)
data class RechargeRequest(val amount: Double)

data class InvestmentResponse(val investment: Double)
data class InvestRequest(val amount: Double)
data class DivestRequest(val amount: Double)

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
    val returnGiven: Double,
    val balanceBefore: Double,
    val balanceAfter: Double,
    val createdAt: String
)

data class InterestResponse(val interest: Double)

data class AliasUpdateRequest(val alias: String)
data class WalletDetailsResponse(
    val id: Int,
    val balance: Double,
    val invested: Double,
    val cbu: String,
    val alias: String
)
