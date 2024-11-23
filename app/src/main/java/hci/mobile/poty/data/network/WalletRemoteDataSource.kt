package hci.mobile.poty.data.network

import hci.mobile.poty.data.network.api.WalletApiService
import hci.mobile.poty.data.network.model.NetworkAlias
import hci.mobile.poty.data.network.model.NetworkBalance
import hci.mobile.poty.data.network.model.NetworkCard
import hci.mobile.poty.data.network.model.NetworkRechargeRequest
import hci.mobile.poty.data.network.model.NetworkRechargeResponse
import hci.mobile.poty.data.network.model.NetworkWalletDetails

class WalletRemoteDataSource(
    private val walletApiService: WalletApiService
) : RemoteDataSource() {

    suspend fun getCards(): List<NetworkCard> {
        return handleApiResponse {
            walletApiService.getCards()
        }
    }

    suspend fun addCard(card: NetworkCard): NetworkCard {
        return handleApiResponse {
            walletApiService.addCard(card)
        }
    }

    suspend fun deleteCard(cardId: Int) {
        handleApiResponse {
            walletApiService.deleteCard(cardId)
        }
    }

    suspend fun getBalance() : NetworkBalance {
        return handleApiResponse {
            walletApiService.getBalance()
        }
    }

    suspend fun recharge(rechargeRequest: NetworkRechargeRequest) : NetworkRechargeResponse {
        return handleApiResponse {
            walletApiService.recharge(rechargeRequest)
        }
    }

    suspend fun getWalletDetails() : NetworkWalletDetails {
        return handleApiResponse {
            walletApiService.getWalletDetails()
        }
    }

    suspend fun updateAlias(alias : NetworkAlias)  {
        return handleApiResponse {
            walletApiService.updateAlias(alias)
        }
    }
}