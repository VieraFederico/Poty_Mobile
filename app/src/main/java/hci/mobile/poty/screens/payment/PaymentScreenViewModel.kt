package hci.mobile.poty.screens.payment

import android.util.Log
import hci.mobile.poty.classes.CardResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import hci.mobile.poty.MyApplication
import hci.mobile.poty.data.model.BalancePayment
import hci.mobile.poty.data.model.CardPayment
import hci.mobile.poty.data.repository.PaymentRepository
import hci.mobile.poty.data.repository.WalletRepository
import hci.mobile.poty.screens.dashboard.DashboardViewModel
import hci.mobile.poty.screens.dashboard.DashboardViewModel.Companion
import hci.mobile.poty.screens.register.RegistrationViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PaymentScreenViewModel(
    private val paymentRepository: PaymentRepository,
    private val walletRepository: WalletRepository
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


    init{
        viewModelScope.launch {
            fetchBalance()
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
            android.util.Log.e(DashboardViewModel.TAG, "Error fetching balance", e)
        }
    }

    fun validateEmail(): Boolean {
        return if (_state.value.email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(_state.value.email).matches()) {
            setErrorMessage("El correo electrónico es inválido o está vacío.")
            false
        } else {
            setErrorMessage("")
            true
        }
    }

    fun validateBalance(): Boolean {
        return if ( _state.value.type == PaymentType.BALANCE && (_state.value.request.amount <= 0 || _state.value.request.amount > _state.value.balance)) {
            setErrorMessage("El monto debe ser mayor a 0 y menor al balance disponible.")
            false
        } else {
            setErrorMessage("")
            true
        }
    }

    fun validateLink(): Boolean {
        return if (_state.value.paymentLink.isEmpty() || _state.value.paymentLink.length != 35) {
            setErrorMessage("El link proporcionado es inválido o está vacío.")
            false
        } else {
            setErrorMessage("")
            true
        }
    }

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
                    email = email,
                    request = currentRequest.copy(receiverEmail = email)
                )
            } else if (currentRequest is PaymentRequest.CardPayment) {
                _state.value = _state.value.copy(
                    email = email,
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


            try {
                _state.update { it.copy(isLoading = true) } // Marca como cargando


                if (state.type == PaymentType.BALANCE) {
                    paymentRepository.payWithBalance(
                        BalancePayment(
                            description = state.description,
                            type = "BALANCE",
                            receiverEmail = state.email,
                            amount = state.request.amount
                        )
                    )
                } else {
                    paymentRepository.payWithCard(
                        CardPayment(
                            description = state.description,
                            type = "CARD",
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
                    app.paymentRepository,
                    app.walletRepository
                ) as T
            }
        }
    }
}
