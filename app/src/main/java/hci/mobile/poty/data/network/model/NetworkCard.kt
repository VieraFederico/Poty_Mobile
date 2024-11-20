package hci.mobile.poty.data.network.model

import hci.mobile.poty.data.model.Card
import hci.mobile.poty.data.model.CardType
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

@Serializable
class NetworkCard(
    var id: Int?,
    var number: String,
    var expirationDate: String,
    var fullName: String,
    var cvv: String? = null,
    var type: String,
    var createdAt: String?,
    var updatedAt: String?
) {
    fun asModel(): Card {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault(Locale.Category.FORMAT))
        return Card(
            id = id,
            number = number,
            expirationDate = expirationDate,
            fullName = fullName,
            cvv = cvv,
            type = when (type) { "DEBIT" -> CardType.DEBIT else -> CardType.CREDIT },
            createdAt = createdAt?.let { dateFormat.parse(createdAt!!) },
            updatedAt = updatedAt?.let { dateFormat.parse(updatedAt!!) }
        )
    }
}