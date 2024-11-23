package hci.mobile.poty.data.repository

import hci.mobile.poty.data.model.Alias
import hci.mobile.poty.data.model.Balance
import hci.mobile.poty.data.model.Card
import hci.mobile.poty.data.model.RechargeRequest
import hci.mobile.poty.data.model.RechargeResponse
import hci.mobile.poty.data.model.WalletDetails
import hci.mobile.poty.data.network.WalletRemoteDataSource
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class WalletRepository(
    private val remoteDataSource: WalletRemoteDataSource
) {
    // Mutex to make writes to cached values thread-safe.
    private val cardsMutex = Mutex()
    // Cache of the latest sports got from the network.
    private var cards: List<Card> = emptyList()


    suspend fun getCards(refresh: Boolean = false): List<Card> {
        if (refresh || cards.isEmpty()) {
            val result = remoteDataSource.getCards()
            // Thread-safe write to sports
            cardsMutex.withLock {
                this.cards = result.map { it.asModel() }
            }
        }

        return cardsMutex.withLock { this.cards }
    }

    suspend fun addCard(card: Card) : Card {
        val newCard = remoteDataSource.addCard(card.asNetworkModel()).asModel()
        cardsMutex.withLock {
            this.cards = emptyList()
        }
        return newCard
    }

    suspend fun deleteCard(cardId: Int) {
        remoteDataSource.deleteCard(cardId)
        cardsMutex.withLock {
            this.cards = emptyList()
        }
    }

    suspend fun getBalance() : Balance {
        return remoteDataSource.getBalance().asModel()

    }

    suspend fun recharge(rechargeRequest: RechargeRequest) : RechargeResponse {
        return remoteDataSource.recharge(rechargeRequest.asNetworkModel()).asModel()
    }

    suspend fun getWalletDetails() : WalletDetails {
        return remoteDataSource.getWalletDetails().asModel()
    }

    suspend fun updateAlias(alias : Alias) {
        return remoteDataSource.updateAlias(alias.asNetworkModel())
    }
}