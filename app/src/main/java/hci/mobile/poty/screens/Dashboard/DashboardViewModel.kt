package hci.mobile.poty.screens.Dashboard

import androidx.lifecycle.ViewModel
import hci.mobile.poty.classes.CreditCard
import hci.mobile.poty.classes.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class DashboardViewModel : ViewModel() {
    // MutableStateFlow for state management
    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state

    fun toggleBalanceVisibility() {
        _state.update { currentState ->
            currentState.copy(isBalanceVisible = !currentState.isBalanceVisible)
        }
    }

    fun addCreditCard(newCard: CreditCard) {
        _state.update { currentState ->
            currentState.copy(creditCards = currentState.creditCards + newCard)
        }
    }

    fun addTransaction(newTransaction: Transaction) {
        _state.update { currentState ->
            currentState.copy(transactions = currentState.transactions + newTransaction)
        }
    }
    // Any other business logic related to the Dashboard can go here.
}