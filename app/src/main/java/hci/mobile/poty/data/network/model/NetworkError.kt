package hci.mobile.poty.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkError(
    val message: String
)