package hci.mobile.poty.data.network.model

import hci.mobile.poty.data.model.LinkPaymentData
import hci.mobile.poty.data.model.LinkPaymentType

class NetworkLinkPaymentData (
    val id: Int,
    val type: String,
    val amount: Float,
    val balanceBefore: Float,
    val balanceAfter: Float,
    val pending: Boolean,
    val linkUuid: String?,
    val createdAt: String,
    val updatedAt: String,
    val card: NetworkCard?
){
    fun asModel(): LinkPaymentData {
        return LinkPaymentData(
            id = id,
            type = LinkPaymentType.valueOf(type),
            amount = amount,
            balanceBefore = balanceBefore,
            balanceAfter = balanceAfter,
            pending = pending,
            linkUuid = linkUuid,
            createdAt = createdAt,
            updatedAt = updatedAt,
            card = card?.asModel()
        )
    }
}