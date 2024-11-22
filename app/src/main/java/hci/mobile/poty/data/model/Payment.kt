package hci.mobile.poty.data.model

import hci.mobile.poty.data.network.model.NetworkPayment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Payment(
    var id: Int,
    var type: String,
    var amount: Float,
    var balanceBefore: Double,
    var balanceAfter: Double,
    var pending: Boolean,
    var linkUuid: String? = null,
    var createdAt: Date? = null,
    var updatedAt: Date? = null,
    var card: Card? = null // Relación con el modelo Card
)
