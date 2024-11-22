package hci.mobile.poty.data.network.model

import hci.mobile.poty.data.model.BalancePayment
import hci.mobile.poty.data.model.Card
import hci.mobile.poty.data.model.CardType
import java.text.SimpleDateFormat
import java.util.Locale

class NetworkBalancePayment (
    val amount: Double,
    val description: String,
    val type: String = "BALANCE",
    val receiverEmail: String

)