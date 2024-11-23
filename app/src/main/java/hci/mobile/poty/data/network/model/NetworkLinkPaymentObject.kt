package hci.mobile.poty.data.network.model

import hci.mobile.poty.data.model.LinkPaymentData
import kotlinx.serialization.Serializable


@Serializable
class NetworkLinkPaymentObject (
    val payment: NetworkLinkPaymentData
){
    suspend fun asModel(): LinkPaymentData {
        return payment.asModel()

    }
}