package hci.mobile.poty.data.model

import hci.mobile.poty.data.network.model.NetworkLinkUuid

class LinkUuid (
    val linkUuid: String
){
    suspend fun asNetworkModel(): NetworkLinkUuid {
        return NetworkLinkUuid(
            linkUuid = linkUuid
        )

    }
}