
package hci.mobile.poty.data.model

import hci.mobile.poty.data.network.model.NetworkBalancePayment
import hci.mobile.poty.data.network.model.NetworkLinkPayment

class LinkPayment  (
    val amount: Double,
    val description: String,
    val type: String = "LINK"
) {
    fun asNetworkModel(): NetworkLinkPayment {
        return NetworkLinkPayment(
            amount = amount,
            description = description,
            type = type
        )
    }
}