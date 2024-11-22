package hci.mobile.poty.data.network.model

import hci.mobile.poty.data.model.CardPayment
import kotlinx.serialization.Serializable

@Serializable
class NetworkCardPayment(
    val amount: Double,
    val description: String,
    val type: String,
    val cardId: Int,
    val receiverEmail: String
){
    fun asModel(): CardPayment {
        return CardPayment(
            amount = amount,
            description = description,
            type = type,
            cardId = cardId,
            receiverEmail = receiverEmail
        )
    }
}
