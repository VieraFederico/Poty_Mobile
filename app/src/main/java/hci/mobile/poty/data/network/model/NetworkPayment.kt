package hci.mobile.poty.data.network.model

import hci.mobile.poty.data.model.Payment
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

@Serializable
class NetworkPayment(
    var id: Int,
    var type: String,
    var amount: Float,
    var balanceBefore: Float,
    var balanceAfter: Float,
    var pending: Boolean,
    var linkUuid: String? = null,
    var createdAt: String,
    var updatedAt: String,
    var card: NetworkCard? // Relación con NetworkCard
) {
    fun asModel(): Payment {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault(Locale.Category.FORMAT))
        return Payment(
            id = id,
            type = type,
            amount = amount,
            balanceBefore = balanceBefore,
            balanceAfter = balanceAfter,
            pending = pending,
            linkUuid = linkUuid,
            createdAt = dateFormat.parse(createdAt),
            updatedAt = dateFormat.parse(updatedAt),
            card = card?.asModel() // Convierte NetworkCard a Card si no es null
        )
    }
}
