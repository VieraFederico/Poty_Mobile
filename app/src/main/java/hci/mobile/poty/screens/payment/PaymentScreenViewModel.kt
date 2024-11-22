package hci.mobile.poty.screens.payment

import hci.mobile.poty.classes.CardResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import hci.mobile.poty.MyApplication
import hci.mobile.poty.data.model.BalancePayment
import hci.mobile.poty.data.model.CardPayment
import hci.mobile.poty.data.repository.PaymentRepository
import hci.mobile.poty.screens.register.RegistrationViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PaymentScreenViewModel(
    private val paymentRepository: PaymentRepository
) : ViewModel() {

    private val _state = MutableStateFlow(
        PaymentScreenState(
            currentStep = 1,
            request = PaymentRequest.BalancePayment(
                amount = 0.0,
                description = "",
                receiverEmail = ""
            ),
            description = "",
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

            if (state.type == PaymentType.BALANCE) {
                paymentRepository.payWithBalance(
                    BalancePayment(
                        description = state.description,
                        type = state.type.toString(),
                        receiverEmail = state.email,
                        amount = state.request.amount
                    )
                )
            } else {
                paymentRepository.payWithCard(
                    CardPayment(
                        description = state.description,
                        type = state.type.toString(),
                        receiverEmail = state.email,
                        amount = state.request.amount,
                        cardId = state.selectedCard?.id ?: 0
                    )
                )
            }

            try {
                _state.update { it.copy(isLoading = true) } // Marca como cargando

                if (state.type == PaymentType.BALANCE) {
                    paymentRepository.payWithBalance(
                        BalancePayment(
                            description = state.description,
                            type = state.type.toString(),
                            receiverEmail = state.email,
                            amount = state.request.amount
                        )
                    )
                } else {
                    paymentRepository.payWithCard(
                        CardPayment(
                            description = state.description,
                            type = state.type.toString(),
                            receiverEmail = state.email,
                            amount = state.request.amount,
                            cardId = state.selectedCard?.id ?: 0
                        )
                    )
                }

                _state.update { it.copy(isLoading = false, errorMessage = "") }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error al procesar el pago: ${e.message}"
                    )
                }
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

    fun onDescriptionChange(newDescription: String) {
        _state.update { it.copy(description = newDescription, errorMessage = "") }
    }

    companion object {
        const val TAG = "UI Layer"

        fun provideFactory(
            app: MyApplication
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PaymentScreenViewModel(
                    app.paymentRepository
                ) as T
            }
        }
    }
}
