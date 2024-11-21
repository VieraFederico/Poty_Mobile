package hci.mobile.poty.data.network.model

import hci.mobile.poty.data.model.Balance

import kotlinx.serialization.Serializable


@Serializable
class NetworkBalance (
    var balance: Float
)
{
    fun asModel(): Balance {
        return Balance(
            balance = balance
        )

    }
}