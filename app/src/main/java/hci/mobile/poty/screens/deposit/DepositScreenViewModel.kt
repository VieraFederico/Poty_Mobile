package hci.mobile.poty.screens.deposit

import android.content.Context
import hci.mobile.poty.data.model.Card
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import hci.mobile.poty.MyApplication
import hci.mobile.poty.R
import hci.mobile.poty.classes.CreditCard
import hci.mobile.poty.data.repository.WalletRepository
import hci.mobile.poty.screens.register.RegistrationViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import hci.mobile.poty.data.model.RechargeRequest
import kotlinx.coroutines.launch

class DepositScreenViewModel(
    private val walletRepository: WalletRepository,
    context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(DepositScreenState())
    val state: StateFlow<DepositScreenState> = _state
    val context by lazy { context }
    var onDepositSuccess: (() -> Unit)? = null // Callback para navegar al Dashboard

    init{
        viewModelScope.launch {
            fetchCreditCards()
        }
    }

    private fun fetchCreditCards() {
        viewModelScope.launch {
            try {
                val cards = walletRepository.getCards(refresh = true)
                _state.update { currentState ->
                    currentState.copy(creditCards = cards)
                }
            } catch (e: Exception) {
                _state.update { currentState ->
                    val errorMessage = context.getString(R.string.error_loading_cards, e.message)
                    currentState.copy(errorMessage = errorMessage)
                }
            }
        }
    }
    fun onNumberChange(newNumber: Float) {
        _state.value = _state.value.copy(number = newNumber)
    }

    fun onDepositClick() {
        val currentState = _state.value
        when {
            currentState.number <= 0 -> {
                val errorMessage = context.getString(R.string.amount_must_be_greater_than_zero)
                _state.value = currentState.copy(errorMessage = errorMessage)
            }

            currentState.selectedCard == null -> {
                // Kotlin code
                val errorMessage = context.getString(R.string.select_card_to_continue)
                _state.value =  currentState.copy(errorMessage = errorMessage)

            }

            else -> {

                viewModelScope.launch {
                    walletRepository.recharge(RechargeRequest(currentState.number))
                }
                onDepositSuccess?.invoke()



                _state.value = currentState.copy(
                    errorMessage = null
                )
            }
        }
    }

    fun onCardSelect(card: Card) {
        _state.value = _state.value.copy(selectedCard = card)
    }


    fun deleteCreditCard(cardId: Int) {
        _state.update { currentState ->
            currentState.copy(creditCards = currentState.creditCards.filter { it.id != cardId })
        }
        viewModelScope.launch {
            walletRepository.deleteCard(cardId)
        }
    }


    companion object {
        const val TAG = "UI Layer"

        fun provideFactory(
            app: MyApplication,
            context: Context
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DepositScreenViewModel(
                    app.walletRepository,
                    context = context
                ) as T
            }
        }
    }
}
