package hci.mobile.poty.data.network.model

import kotlinx.serialization.Serializable

@Serializable
class NetworkLinkPayment(
    val type: String,
    val cardId: Int? = null
)