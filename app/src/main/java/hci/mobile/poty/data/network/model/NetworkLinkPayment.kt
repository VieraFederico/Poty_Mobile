package hci.mobile.poty.data.network.model

class NetworkLinkPayment(
    val amount: Double,
    val description: String,
    val type: String = "LINK"
)