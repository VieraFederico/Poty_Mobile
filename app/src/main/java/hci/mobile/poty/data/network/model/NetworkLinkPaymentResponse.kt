package hci.mobile.poty.data.network.model

import hci.mobile.poty.data.model.LinkPaymentResponse
import kotlinx.serialization.Serializable

@Serializable
class NetworkLinkPaymentResponse (
    val success: Boolean
) {
    fun asModel(): LinkPaymentResponse {
        return LinkPaymentResponse(
            success = success
        )

    }
}