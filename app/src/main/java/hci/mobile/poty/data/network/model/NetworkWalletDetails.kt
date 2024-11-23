package hci.mobile.poty.data.network.model


import hci.mobile.poty.data.model.Payment
import hci.mobile.poty.data.model.WalletDetails
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

@Serializable
class NetworkWalletDetails(
    var id: Int,
    var balance: Float,
    var invested: Float,
    var cbu: String,
    var alias: String,
    var createdAt: String,
    var updatedAt: String
) {
    fun asModel(): WalletDetails {
        return WalletDetails(
            id = id,
            balance = balance,
            invested = invested,
            cbu = cbu,
            alias = alias,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}

