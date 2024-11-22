package hci.mobile.poty.data.model

import hci.mobile.poty.data.network.model.NetworkBalancePayment

class BalancePayment  (
    val amount: Float,
    val description: String,
    val type: String,
    val receiverEmail: String
) {
    fun asNetworkModel(): NetworkBalancePayment {
        return NetworkBalancePayment(
            amount = amount,
            description = description,
            type = type,
            receiverEmail = receiverEmail
        )
    }
}