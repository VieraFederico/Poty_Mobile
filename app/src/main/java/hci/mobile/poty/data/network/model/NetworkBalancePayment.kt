package hci.mobile.poty.data.network.model

import hci.mobile.poty.data.model.BalancePayment
import hci.mobile.poty.data.model.Card
import hci.mobile.poty.data.model.CardType
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

@Serializable
class NetworkBalancePayment (
    val amount: Float,
    val description: String,
    val type: String,
    val receiverEmail: String

){
    fun asModel(): BalancePayment {

    return BalancePayment(
        amount = amount,
        description = description,
        type = type,
        receiverEmail = receiverEmail
    )
}
}