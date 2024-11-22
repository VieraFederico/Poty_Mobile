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
import hci.mobile.poty.data.network.model.NetworkBalancePayment
import hci.mobile.poty.data.network.model.NetworkCardPayment

class PaymentRepository (
    private val remoteDataSource: PaymentRemoteDataSource
){

        suspend fun payWithBalance(balancePayment: BalancePayment) {
            remoteDataSource.payWithBalance(balancePayment.asNetworkModel())
        }

        suspend fun payWithCard(cardPayment: CardPayment) {
            remoteDataSource.payWithCard(cardPayment.asNetworkModel())
        }

        suspend fun payWithLink(linkPayment: LinkPayment){
            remoteDataSource.payWithLink(linkPayment.asNetworkModel())
        }



}