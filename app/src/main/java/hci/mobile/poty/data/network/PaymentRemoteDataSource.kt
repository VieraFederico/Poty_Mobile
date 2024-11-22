package hci.mobile.poty.data.network

import hci.mobile.poty.SessionManager
import hci.mobile.poty.data.model.BalancePayment
import hci.mobile.poty.data.model.CardPayment
import hci.mobile.poty.data.model.LinkPayment
import hci.mobile.poty.data.network.api.PaymentApiService
import hci.mobile.poty.data.network.model.NetworkBalancePayment
import hci.mobile.poty.data.network.model.NetworkCard
import hci.mobile.poty.data.network.model.NetworkCardPayment
import hci.mobile.poty.data.network.model.NetworkLinkPayment
import hci.mobile.poty.data.network.model.NetworkPayment

class PaymentRemoteDataSource (
    private val paymentApiService: PaymentApiService
) : RemoteDataSource() {

    suspend fun payWithBalance(balancePayment: NetworkBalancePayment) {
         return handleApiResponse {
            paymentApiService.payWithBalance(balancePayment)
        }
    }

    suspend fun payWithCard(cardPayment: NetworkCardPayment) {
         return handleApiResponse {
            paymentApiService.payWithCard(cardPayment)
        }
    }

    suspend fun payWithLink(linkPayment: NetworkLinkPayment){
        return handleApiResponse {
            paymentApiService.payWithLink(linkPayment)
        }
    }

    suspend fun getPayments(): List<NetworkPayment> {
        return handleApiResponse {
            paymentApiService.getPayments()
        }
    }

}

