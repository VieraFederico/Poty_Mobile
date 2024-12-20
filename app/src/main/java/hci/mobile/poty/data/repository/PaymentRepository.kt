package hci.mobile.poty.data.repository

import hci.mobile.poty.data.model.Balance
import hci.mobile.poty.data.model.BalancePayment
import hci.mobile.poty.data.model.Card
import hci.mobile.poty.data.model.RechargeRequest
import hci.mobile.poty.data.model.RechargeResponse
import hci.mobile.poty.data.model.User
import hci.mobile.poty.data.network.PaymentRemoteDataSource
import hci.mobile.poty.data.network.WalletRemoteDataSource
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import hci.mobile.poty.data.model.CardPayment
import hci.mobile.poty.data.model.LinkPayment
import hci.mobile.poty.data.model.LinkPaymentData
import hci.mobile.poty.data.model.LinkPaymentResponse
import hci.mobile.poty.data.model.LinkUuid
import hci.mobile.poty.data.model.NewPaymentLink
import hci.mobile.poty.data.network.model.NetworkBalancePayment
import hci.mobile.poty.data.network.model.NetworkCardPayment
import hci.mobile.poty.data.model.Payment
import hci.mobile.poty.data.network.model.NetworkLinkPaymentData
import hci.mobile.poty.data.network.model.NetworkLinkPaymentResponse
import kotlinx.coroutines.sync.withLock

class PaymentRepository (
    private val remoteDataSource: PaymentRemoteDataSource
){
    // Mutex to make writes to cached values thread-safe
    private val paymentsMutex = Mutex()
    // Cache of the latest payments fetched from the network
    private var payments: List<Payment> = emptyList()


    suspend fun getPayments(refresh: Boolean = false): List<Payment> {
        if (refresh || payments.isEmpty()) {
            val result = remoteDataSource.getPayments()
            // Thread-safe write to payments
            paymentsMutex.withLock {
                this.payments = result.map { it.asModel() }
            }
        }

        return paymentsMutex.withLock { this.payments }
    }

    suspend fun payWithBalance(balancePayment: BalancePayment) {
            remoteDataSource.payWithBalance(balancePayment.asNetworkModel())
        }

    suspend fun payWithCard(cardPayment: CardPayment) {
        remoteDataSource.payWithCard(cardPayment.asNetworkModel())
    }

    suspend fun generateLink(linkPayment: NewPaymentLink): LinkUuid{
        return remoteDataSource.generateLink(linkPayment.asNetworkModel()).asModel()
    }

    suspend fun settlePayment(linkPayment: LinkPayment, linkUuid: String): LinkPaymentResponse {
        return remoteDataSource.settlePayment(linkPayment.asNetworkModel(), linkUuid).asModel()
    }

    suspend fun getPaymentData(linkUuid: String): LinkPaymentData {
        return remoteDataSource.getPaymentData(linkUuid).asModel()
    }



}