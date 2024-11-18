package hci.mobile.poty.screens.addCard

import androidx.lifecycle.ViewModel
import hci.mobile.poty.classes.CreditCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class AddCardScreenViewModel : ViewModel() {

    private val _state = MutableStateFlow(AddCardScreenState())
    val state: StateFlow<AddCardScreenState> = _state

    fun onAddCardClick() {
        val currentState = _state.value

        if (currentState.number.isBlank() || currentState.owner.isBlank()) {
            _state.value = currentState.copy(errorMessage = "Por favor, complete todos los campos")
            return
        }

        _state.value = currentState.copy(errorMessage = null)

        // Llamada de API
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

    fun addCard() {
        if (_state.value.isValid) {
            // TODO: Implementar lógica para guardar la tarjeta
            println("Tarjeta agregada exitosamente: ${_state.value}")
        } else {
            println("Detalles de la tarjeta inválidos: ${_state.value.errorMessage}")
        }
    }
}

