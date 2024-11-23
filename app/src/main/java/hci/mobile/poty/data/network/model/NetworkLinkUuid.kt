package hci.mobile.poty.data.network.model

import hci.mobile.poty.data.model.LinkUuid
import kotlinx.serialization.Serializable

@Serializable
class NetworkLinkUuid (
    val linkUuid: String

) {
    suspend fun asModel(): LinkUuid {
        return LinkUuid(
            linkUuid = linkUuid
        )

    }
}