package hci.mobile.poty.data.network.model

import hci.mobile.poty.data.model.CardPayment
import kotlinx.serialization.Serializable

@Serializable
class NetworkCardPayment(
    val amount: Double,
    val description: String,
    val type: String = "CARD",
    val cardId: Int,
    val receiverEmail: String
)