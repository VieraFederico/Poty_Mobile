package hci.mobile.poty.data.network

import hci.mobile.poty.SessionManager
import hci.mobile.poty.data.model.BalancePayment
import hci.mobile.poty.data.model.CardPayment
import hci.mobile.poty.data.network.api.PaymentApiService
import hci.mobile.poty.data.network.model.NetworkBalancePayment
import hci.mobile.poty.data.network.model.NetworkCardPayment

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

}

