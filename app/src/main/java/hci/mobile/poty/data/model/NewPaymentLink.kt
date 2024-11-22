package hci.mobile.poty.data.model


import hci.mobile.poty.data.network.model.NetworkNewPaymentLink

class NewPaymentLink  (
    val amount: Double,
    val description: String,
    val type: String = "LINK"
) {
    fun asNetworkModel(): NetworkNewPaymentLink {
        return NetworkNewPaymentLink(
            amount = amount,
            description = description,
            type = type
        )
    }
}