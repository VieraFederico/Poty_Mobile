package hci.mobile.poty.screens.Deposit

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import hci.mobile.poty.classes.CreditCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DepositScreenViewModel : ViewModel() {

    private val _state = MutableStateFlow(DepositScreenState())
    val state: StateFlow<DepositScreenState> = _state

    fun onNumberChange(newNumber: Float) {
        _state.value = _state.value.copy(number = newNumber)
    }

    fun onDepositClick() {
        if (_state.value.number <= 0) {
            _state.value = _state.value.copy(errorMessage = "La cantidad a ingresar debe ser mayor a 0.")
        } else {
            // API
            _state.value = _state.value.copy(errorMessage = null)
        }
    }

    fun addCreditCard(newCard: CreditCard) {
        _state.update { currentState ->
            currentState.copy(creditCards = currentState.creditCards + newCard)
        }
    }

    fun onCardSelect(card: CreditCard) {
        _state.value = _state.value.copy(selectedCard = card)
    }
}
