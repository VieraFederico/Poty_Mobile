package hci.mobile.poty.data.model

import hci.mobile.poty.data.network.model.NetworkAlias
import hci.mobile.poty.data.network.model.NetworkBalance

class Alias (
    var alias : String
){
    fun asNetworkModel(): NetworkAlias {
        return NetworkAlias(
            alias = alias
        )
    }
}