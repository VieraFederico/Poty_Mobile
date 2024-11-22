package hci.mobile.poty.data.model

import hci.mobile.poty.data.network.model.NetworkToken

class Token (
    var token: String
    ){
    fun asNetworkModel(): NetworkToken{
        return NetworkToken(
            token = token
        )
    }
}

