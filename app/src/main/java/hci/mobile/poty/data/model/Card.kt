package hci.mobile.poty.data.model

import hci.mobile.poty.data.network.model.NetworkCard
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Card(
    var id: Int? = null,
    var number: String,
    var expirationDate: String,
    var fullName: String,
    var cvv: String?,
    var type: CardType,
    var createdAt: Date? = null,
    var updatedAt: Date? = null
) {
    fun asNetworkModel(): NetworkCard {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault(Locale.Category.FORMAT))

        return NetworkCard(
            id = id,
            number = number,
            expirationDate = expirationDate,
            fullName = fullName,
            cvv = cvv,
            type = when (type) { CardType.DEBIT -> "DEBIT" else -> "CREDIT" },
            createdAt = createdAt?.let { dateFormat.format(createdAt!!) },
            updatedAt = updatedAt?.let { dateFormat.format(updatedAt!!) }
        )
    }
}