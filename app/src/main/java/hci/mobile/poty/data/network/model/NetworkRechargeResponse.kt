package hci.mobile.poty.data.network.model

import hci.mobile.poty.data.model.RechargeResponse
import hci.mobile.poty.data.model.User
import java.text.SimpleDateFormat
import java.util.Locale
import kotlinx.serialization.Serializable as serializable

@serializable
class NetworkRechargeResponse (
    var newBalance: Float
){
    fun asModel(): RechargeResponse {

        return RechargeResponse(
            newBalance = newBalance
        )
    }
}