package hci.mobile.poty.data.network

import hci.mobile.poty.SessionManager
import hci.mobile.poty.data.model.BalancePayment
import hci.mobile.poty.data.model.CardPayment
import hci.mobile.poty.data.model.LinkPayment
import hci.mobile.poty.data.model.LinkPaymentResponse
import hci.mobile.poty.data.network.api.PaymentApiService
import hci.mobile.poty.data.network.model.NetworkBalancePayment
import hci.mobile.poty.data.network.model.NetworkCard
import hci.mobile.poty.data.network.model.NetworkCardPayment
import hci.mobile.poty.data.network.model.NetworkLinkPayment
import hci.mobile.poty.data.network.model.NetworkLinkPaymentData
import hci.mobile.poty.data.network.model.NetworkLinkPaymentObject
import hci.mobile.poty.data.network.model.NetworkLinkPaymentResponse
import hci.mobile.poty.data.network.model.NetworkLinkUuid
import hci.mobile.poty.data.network.model.NetworkNewPaymentLink
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

    suspend fun generateLink(linkPayment: NetworkNewPaymentLink): NetworkLinkUuid{
        return handleApiResponse {
            paymentApiService.generateLink(linkPayment)
        }
    }

    suspend fun getPayments(): List<NetworkPayment> {
        return handleApiResponse {
            paymentApiService.getPayments()
        }
    }

    suspend fun settlePayment(linkPayment: NetworkLinkPayment, linkUuid: String): NetworkLinkPaymentResponse {
        return handleApiResponse {
            paymentApiService.settlePayment(linkPayment, linkUuid)
        }
    }

    suspend fun getPaymentData(linkUuid: String): NetworkLinkPaymentObject {
        return handleApiResponse {
            paymentApiService.getPaymentData(linkUuid)
        }
    }

}

