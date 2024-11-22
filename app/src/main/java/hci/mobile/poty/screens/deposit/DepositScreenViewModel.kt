package hci.mobile.poty.screens.deposit

import hci.mobile.poty.data.model.Card
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import hci.mobile.poty.MyApplication
import hci.mobile.poty.classes.CreditCard
import hci.mobile.poty.data.repository.WalletRepository
import hci.mobile.poty.screens.register.RegistrationViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import hci.mobile.poty.data.model.RechargeRequest
import kotlinx.coroutines.launch

class DepositScreenViewModel(
    private val walletRepository: WalletRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DepositScreenState())
    val state: StateFlow<DepositScreenState> = _state

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
                    currentState.copy(errorMessage = "Error al cargar las tarjetas: ${e.message}")
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
                _state.value = currentState.copy(
                    errorMessage = "La cantidad a ingresar debe ser mayor a 0."
                )
            }

            currentState.selectedCard == null -> {
                _state.value = currentState.copy(
                    errorMessage = "Debe seleccionar una tarjeta para continuar."
                )
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

    fun addCreditCard(newCard: Card) {
        _state.update { currentState ->
            currentState.copy(creditCards = currentState.creditCards + newCard)
        }
    }
    fun deleteCreditCard(cardId: Int) {
        _state.update { currentState ->
            currentState.copy(creditCards = currentState.creditCards.filter { it.id != cardId })
        }
    }


    companion object {
        const val TAG = "UI Layer"

        fun provideFactory(
            app: MyApplication
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DepositScreenViewModel(
                    app.walletRepository
                ) as T
            }
        }
    }
}
