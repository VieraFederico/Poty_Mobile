package hci.mobile.poty.data.network.model

import hci.mobile.poty.data.model.LinkPaymentData
import hci.mobile.poty.data.model.LinkPaymentType
import kotlinx.serialization.Serializable

@Serializable
class NetworkLinkPaymentData(
    val id: Int? = null,
    val type: String? = null,
    val amount: Float,
    val balanceBefore: Float? = null,
    val balanceAfter: Float? = null,
    val pending: Boolean? = null,
    val linkUuid: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val card: NetworkCard? = null
) {
    fun asModel(): LinkPaymentData {
        return LinkPaymentData(
            id = id,
            type = type?.let { LinkPaymentType.valueOf(it) } ?: LinkPaymentType.BALANCE,
            amount = amount,
            balanceBefore = balanceBefore ?: 0.0f,
            balanceAfter = balanceAfter ?: 0.0f,
            pending = pending ?: false,
            linkUuid = linkUuid,
            createdAt = createdAt ?: "",
            updatedAt = updatedAt ?: "",
            card = card?.asModel()
        )
    }
}
