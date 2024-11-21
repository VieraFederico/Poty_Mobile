package hci.mobile.poty.screens.payment

import CardResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PaymentScreenViewModel : ViewModel() {

    private val _state = MutableStateFlow(
        PaymentScreenState(
            currentStep = 1,
            request = PaymentRequest.BalancePayment(
                amount = 0.0,
                description = "",
                receiverEmail = ""
            )
        )
    )
    val state: StateFlow<PaymentScreenState> = _state


    fun nextStep() {
        viewModelScope.launch {
            _state.value = _state.value.copy(currentStep = _state.value.currentStep + 1)
        }
    }


    fun previousStep() {
        viewModelScope.launch {
            if (_state.value.currentStep > 1) {
                _state.value = _state.value.copy(currentStep = _state.value.currentStep - 1)
            }
        }
    }


    fun updateEmail(email: String) {
        viewModelScope.launch {
            val currentRequest = _state.value.request
            if (currentRequest is PaymentRequest.BalancePayment) {
                _state.value = _state.value.copy(
                    request = currentRequest.copy(receiverEmail = email)
                )
            } else if (currentRequest is PaymentRequest.CardPayment) {
                _state.value = _state.value.copy(
                    request = currentRequest.copy(receiverEmail = email)
                )
            }
        }
    }


    fun updateAmount(amount: Double) {
        viewModelScope.launch {
            val currentRequest = _state.value.request
            _state.value = _state.value.copy(
                request = when (currentRequest) {
                    is PaymentRequest.BalancePayment -> currentRequest.copy(amount = amount)
                    is PaymentRequest.CardPayment -> currentRequest.copy(amount = amount)
                }
            )
        }
    }

    fun selectCard(cardResponse: CardResponse) {
        viewModelScope.launch {
            val currentRequest = _state.value.request
            val cardId = cardResponse.id
            if (currentRequest is PaymentRequest.CardPayment) {
                _state.value = _state.value.copy(
                    request = currentRequest.copy(cardId = cardId)
                )
            }
        }
    }


    fun onPaymentTypeChange(paymentType: PaymentType) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                type = paymentType
            )
        }
    }

    fun updateLink(link: String) {
        _state.update { it.copy(paymentLink = link, errorMessage = "") }
    }

    fun setErrorMessage(message: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(errorMessage = message)
        }
    }

    fun setLoading(isLoading: Boolean) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = isLoading)
        }
    }

    fun onSubmitPayment() {
        viewModelScope.launch {
            val state = _state.value

            // Verificamos el tipo de pago y armamos el PaymentRequest
            val paymentRequest = when (state.type) {
                PaymentType.CARD -> {
                    PaymentRequest.CardPayment(
                        amount = state.request.amount,
                        description = state.request.description,
                        cardId = state.selectedCard?.id ?: 0, // Si no hay tarjeta seleccionada, usamos 0
                        receiverEmail = state.email // Suponiendo que es necesario el email del receptor
                    )
                }
                PaymentType.BALANCE -> {
                    PaymentRequest.BalancePayment(
                        amount = state.request.amount,
                        description = state.request.description,
                        receiverEmail = state.email // Suponiendo que es necesario el email del receptor
                    )
                }
                else -> {
                    setErrorMessage("Tipo de pago no válido.")
                }
            }

            try {
                //Llamamos API
            } catch (e: Exception) {
                setErrorMessage("Error al procesar el pago: ${e.message}")
            }
        }
    }

    fun extractDataFromLink (link: String) {
        viewModelScope.launch {
            try {
                //Copiar Valores xd
            } catch (e: Exception) {
                setErrorMessage(e.message ?: "Failed to extract payment details from the link")
            }
        }
    }
    fun addCreditCard(newCard: CardResponse) {
        _state.update { currentState ->
            currentState.copy(creditCards = currentState.creditCards + newCard)
        }
    }
    fun onDeleteCard(cardId: Int) {
        _state.update { currentState ->
            currentState.copy(creditCards = currentState.creditCards.filter { it.id != cardId })
        }
    }
}
