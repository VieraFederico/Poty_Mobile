package hci.mobile.poty.data.model

import hci.mobile.poty.data.network.model.NetworkBalance

class Balance (
    var balance: Float
){
    fun asNetworkModel(): NetworkBalance {
        return NetworkBalance(
            balance = balance
        )
    }
}