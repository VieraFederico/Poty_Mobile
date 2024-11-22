package hci.mobile.poty.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import hci.mobile.poty.MyApplication
import hci.mobile.poty.classes.Transaction
import hci.mobile.poty.data.model.Card
import hci.mobile.poty.data.repository.WalletRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val walletRepository: WalletRepository,

) : ViewModel() {
    // MutableStateFlow for state management
    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state

    init{
        viewModelScope.launch {
            fetchBalance()
            fetchCreditCards()
        }
    }

    private suspend fun fetchBalance() {
        try {
            val balanceResponse = walletRepository.getBalance() // Puede lanzar excepciones
            _state.update { currentState ->
                currentState.copy(balance = balanceResponse.balance)
            }
        } catch (e: Exception) {
            // Manejo de error: Actualiza el estado con un mensaje o loguea el error
            _state.update { currentState ->
                currentState.copy(balance = 0f) // Asume balance 0 en caso de error
            }
            // Loguea para depuración
            android.util.Log.e(TAG, "Error fetching balance", e)
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

    fun DeleteCard(cardId: Int) {
        viewModelScope.launch {
            try {
                walletRepository.deleteCard(cardId)
                val updatedCards = walletRepository.getCards(refresh = true) // Refresca las tarjetas
                _state.update { currentState ->
                    currentState.copy(creditCards = updatedCards)
                }
            } catch (e: Exception) {
                _state.update { currentState ->
                    currentState.copy(errorMessage = "Error al eliminar la tarjeta: ${e.message}")
                }
            }
        }
    }




    fun toggleBalanceVisibility() {
        _state.update { currentState ->
            currentState.copy(isBalanceVisible = !currentState.isBalanceVisible)
        }
    }



    companion object {
        const val TAG = "UI Layer"

        fun provideFactory(
            app: MyApplication
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DashboardViewModel(
                    app.walletRepository
                ) as T
            }
        }
    }
}
