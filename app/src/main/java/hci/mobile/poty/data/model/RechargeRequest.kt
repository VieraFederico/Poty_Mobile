package hci.mobile.poty.data.model

import hci.mobile.poty.data.network.model.NetworkRechargeRequest
import hci.mobile.poty.data.network.model.NetworkUser
import java.text.SimpleDateFormat
import java.util.Locale

class RechargeRequest(
    var amount: Float
) {
    fun asNetworkModel(): NetworkRechargeRequest {

        return NetworkRechargeRequest(
            amount = amount

        )
    }
}