package hci.mobile.poty.data.network.model

import kotlinx.serialization.Serializable

@Serializable
class NetworkNewPaymentLink(
    val amount: Float,
    val description: String,
    val type: String
)
