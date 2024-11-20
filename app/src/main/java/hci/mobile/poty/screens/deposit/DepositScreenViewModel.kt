package hci.mobile.poty.screens.deposit

import androidx.lifecycle.ViewModel
import hci.mobile.poty.classes.CreditCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class DepositScreenViewModel : ViewModel() {

    private val _state = MutableStateFlow(DepositScreenState())
    val state: StateFlow<DepositScreenState> = _state

    var onDepositSuccess: (() -> Unit)? = null // Callback para navegar al Dashboard


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
                onDepositSuccess?.invoke()

                // Proceed with deposit logic (API call or further actions)
                _state.value = currentState.copy(
                    errorMessage = null
                )
            }
        }
    }
    fun addCreditCard(newCard: CreditCard) {
        _state.update { currentState ->
            currentState.copy(creditCards = currentState.creditCards + newCard)
        }
    }

    fun onCardSelect(card: CreditCard) {
        _state.update { currentState ->
            currentState.copy(selectedCard = card)
        }
    }
}
