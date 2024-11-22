package hci.mobile.poty.data.network.model

import hci.mobile.poty.data.model.Balance
import kotlinx.serialization.Serializable


@Serializable
class NetworkRechargeRequest (
    var amount: Float
)
