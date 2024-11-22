package hci.mobile.poty.data.model

import hci.mobile.poty.data.network.model.NetworkCode
import hci.mobile.poty.data.network.model.NetworkToken

class Code (
    var code: String
){
    fun asNetworkModel(): NetworkCode {
        return NetworkCode(
            code = code
        )
    }
}
