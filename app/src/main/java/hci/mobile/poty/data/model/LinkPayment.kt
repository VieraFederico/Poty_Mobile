package hci.mobile.poty.data.model

import hci.mobile.poty.data.network.model.NetworkLinkPayment

class LinkPayment (
    val type: LinkPaymentType,
    val cardId: Int? = null
) {
    fun asNetworkModel(): NetworkLinkPayment
    {
        return NetworkLinkPayment(
            type = type.name,
            cardId = cardId
        )
    }
}
