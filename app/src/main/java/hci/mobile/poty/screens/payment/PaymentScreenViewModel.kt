package hci.mobile.poty.screens.payment

import android.util.Log
import android.util.Patterns
import hci.mobile.poty.data.model.Card
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import hci.mobile.poty.MyApplication
import hci.mobile.poty.data.model.BalancePayment
import hci.mobile.poty.data.model.CardPayment
import hci.mobile.poty.data.model.LinkPayment
import hci.mobile.poty.data.model.LinkPaymentType
import hci.mobile.poty.data.network.model.NetworkLinkPayment
import hci.mobile.poty.data.repository.PaymentRepository
import hci.mobile.poty.data.repository.WalletRepository
import hci.mobile.poty.screens.dashboard.DashboardViewModel
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
            request = PaymentRequest.linkPayment(
                amount = 10f,
                description = "",
                type = "LINK",
                linkUuid = ""

            ),
            description = "",
        )
    )
    val state: StateFlow<PaymentScreenState> = _state


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
            Log.e(DashboardViewModel.TAG, "Error fetching balance", e)
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

    fun validateEmail(): Boolean {
        val email = _state.value.email
        return if ( !email.contains('@')) {
            setErrorMessage("El correo electrónico es inválido. Asegúrate de que no esté vacío y contenga una '@'.")
            false
        } else {
            setErrorMessage("") // Limpia el mensaje de error si es válido
            true
        }
    }


    fun validateBalance(): Boolean {
        return if ( _state.value.type == LinkPaymentType.BALANCE && (_state.value.request.amount <= 0 || _state.value.request.amount > _state.value.balance)) {
            setErrorMessage("El monto debe ser mayor a 0 y menor al balance disponible.")
            false
        } else {
            setErrorMessage("")
            true
        }
    }

    fun validateDescription(): Boolean {
        return if (_state.value.description.isBlank()) {
            setErrorMessage("La descripción no puede estar vacía.")
            false
        } else {
            setErrorMessage("")
            true
        }
    }


    fun validateLink(): Boolean {
        return if (_state.value.paymentLink.isEmpty() || _state.value.paymentLink.length != 36) {
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

            _state.value = _state.value.copy(email = email)

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


    fun updateAmount(amount: Float) {
        viewModelScope.launch {
            val currentRequest = _state.value.request
            _state.value = _state.value.copy(
                request = when (currentRequest) {
                    is PaymentRequest.BalancePayment -> currentRequest.copy(amount = amount)
                    is PaymentRequest.CardPayment -> currentRequest.copy(amount = amount)
                    is PaymentRequest.linkPayment -> currentRequest.copy(amount = amount)

                }
            )
        }
    }

    fun selectCard(card: Card) {
        viewModelScope.launch {
            val currentRequest = _state.value.request
            val cardId = card.id
            if (currentRequest is PaymentRequest.CardPayment) {
                _state.value = cardId?.let { currentRequest.copy(cardId = it) }?.let {
                    _state.value.copy(
                        request = it
                    )
                }!!
            }
        }
    }


    fun onPaymentTypeChange(paymentType: LinkPaymentType) {
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
                _state.update { it.copy(isLoading = true) }

                if (state.type == LinkPaymentType.BALANCE) {
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


    fun getPaymentData(linkUuid: String) {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true, errorMessage = "") }

                val response = paymentRepository.getPaymentData(linkUuid)

                _state.update { it.copy(amount = response.amount, isLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, errorMessage = "Error al obtener datos de pago: ${e.message}") }
            }
        }
    }

    fun settlePayment(linkUuid: String) {
        viewModelScope.launch {
            try {
                // Marca el inicio de la carga
                _state.update { it.copy(isLoading = true, errorMessage = "") }

                // Llama al repositorio para procesar el pago
                paymentRepository.settlePayment(
                    linkPayment = LinkPayment(
                        type = _state.value.type,
                        cardId = _state.value.selectedCard?.id
                    ),
                    linkUuid = linkUuid
                )

                // Finaliza la carga y limpia mensajes de error
                _state.update { it.copy(isLoading = false, errorMessage = "") }
            } catch (e: Exception) {
                // Maneja el error actualizando el estado con un mensaje de error
                _state.update { it.copy(isLoading = false, errorMessage = "Error al procesar el pago: ${e.message}") }
            }
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
