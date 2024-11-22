package hci.mobile.poty.screens.addCard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import hci.mobile.poty.MyApplication
import hci.mobile.poty.classes.CreditCard
import hci.mobile.poty.data.model.Card
import hci.mobile.poty.data.repository.WalletRepository
import hci.mobile.poty.screens.deposit.DepositScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddCardScreenViewModel(
    private val walletRepository: WalletRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AddCardScreenState())
    val state: StateFlow<AddCardScreenState> = _state

    var onAddCardSuccess: (() -> Unit)? = null // Callback para navegar al Dashboard

    fun onAddCardClick() {
        val currentState = _state.value

        if (currentState.number.isBlank() || currentState.owner.isBlank()) {
            _state.value = currentState.copy(errorMessage = "Por favor, complete todos los campos")
            return
        }

        viewModelScope.launch {
            try {
                // Mapea los datos ingresados a un modelo `Card` y llama al repositorio
                val card = Card(
                   fullName = currentState.owner,
                    cvv = currentState.cvv,
                    number = currentState.number,
                    expirationDate = currentState.exp,
                    type = currentState.type
                )
                walletRepository.addCard(card)


                _state.update { AddCardScreenState() }
                onAddCardSuccess?.invoke()
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = "Error al agregar la tarjeta: ${e.message}") }
            }
        }
    }

    fun onBankChange(newBank: String) {
        _state.update { it.copy(bank = newBank) }
        validateForm()
    }

    fun onNumberChange(newNumber: String) {
        _state.update { it.copy(number = newNumber) }
        validateForm()
    }

    fun onOwnerChange(newOwner: String) {
        _state.update { it.copy(owner = newOwner) }
        validateForm()
    }

    fun onCVVChange(newCVV: String) {
        _state.update { it.copy(cvv = newCVV) }
        validateForm()
    }

    fun onExpDateChange(newExp: String) {
        _state.update { it.copy(exp = newExp) }
        validateForm()
    }

    private fun validateForm() {
        val card = CreditCard(
            bank = _state.value.bank,
            number = _state.value.number,
            owner = _state.value.owner,
            cvv = _state.value.cvv,
            exp = _state.value.exp
        )

        val isValid = card.isValidCardNumber() && card.isValidCVV() && card.isValidExpDate()

        val errorMessage = when {
            !card.isValidCardNumber() -> "Número de tarjeta inválido. Debe tener 16 dígitos."
            !card.isValidCVV() -> "CVV inválido. Debe tener 3 dígitos."
            !card.isValidExpDate() -> "Fecha de vencimiento inválida. Usa el formato MM/AA."

            else -> null
        }

        _state.update { it.copy(isValid = isValid, errorMessage = errorMessage) }
    }


    companion object {
        const val TAG = "UI Layer"

        fun provideFactory(
            app: MyApplication
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AddCardScreenViewModel(
                    app.walletRepository
                ) as T
            }
        }
    }
}

